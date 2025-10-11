package com.logicerror.e_learning.courses.services;

import com.logicerror.e_learning.courses.constants.CourseLevel;
import com.logicerror.e_learning.courses.dtos.CourseDto;
import com.logicerror.e_learning.courses.projections.CourseListProjectionDTOMapper;
import com.logicerror.e_learning.sections.dtos.SectionDto;
import com.logicerror.e_learning.courses.entities.Course;
import com.logicerror.e_learning.sections.entities.Section;
import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.repositories.UserEnrollmentsRepository;
import com.logicerror.e_learning.courses.requests.CreateCourseRequest;
import com.logicerror.e_learning.courses.requests.UpdateCourseRequest;
import com.logicerror.e_learning.sections.services.DefaultSectionService;
import com.logicerror.e_learning.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Facade service that orchestrates course operations
 * Provides a clean interface for controllers
 */
@Service
@RequiredArgsConstructor
public class DefaultCourseService implements CourseService {
    
    private final CourseQueryService courseQueryService;
    private final CourseDtoService courseDtoService;
    private final CourseCommandService courseCommandService;
    private final UserService userService;
    private final UserEnrollmentsRepository userEnrollmentsRepository;
    private final DefaultSectionService sectionService;

    @Override
    public Page<CourseDto> getAllCourses(Pageable pageable) {
        Page<CourseListProjectionDTOMapper> courses = courseQueryService.getAllCourses(pageable);
        return courses.map(courseDtoService::convertToDto);
    }

    @Override
    public CourseDto getCourseById(Long courseId) {
        User currentUser = userService.getAuthenticatedUser();
        boolean isEnrolled = userEnrollmentsRepository.existsByUserIdAndCourseId(currentUser.getId(), courseId);
        if(isEnrolled) {
            return courseDtoService.convertToDto(courseQueryService.getCourseById(courseId));
        }
        return courseDtoService.convertToDto(courseQueryService.getCoursePreviewById(courseId));
    }

    @Override
    public Page<CourseDto> getCoursesByCategory(String category, Pageable pageable) {
        Page<CourseListProjectionDTOMapper> courses = courseQueryService.getCoursesByCategory(category, pageable);
        return courses.map(courseDtoService::convertToDto);
    }

    @Override
    public Page<CourseDto> getCoursesByLevel(CourseLevel level, Pageable pageable) {
        Page<CourseListProjectionDTOMapper> courses = courseQueryService.getCoursesByLevel(level, pageable);
        return courses.map(courseDtoService::convertToDto);
    }

    @Override
    public Page<SectionDto> getCourseSections(Long courseId, Pageable pageable) {
        Page<Section> sections = courseQueryService.getCourseSections(courseId, pageable);
        return sections.map(sectionService::convertToDto);
    }

    @Override
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public CourseDto updateCourse(Long courseId, UpdateCourseRequest request) {
        Course course = courseCommandService.updateCourse(courseId, request);
        return courseDtoService.convertToDto(course);
    }

    @Override
    @PreAuthorize("hasRole('TEACHER')")
    public CourseDto createCourse(CreateCourseRequest request, MultipartFile thumbnail) {
        Course course = courseCommandService.createCourse(request, thumbnail);
        return courseDtoService.convertToDto(course);
    }

    @Override
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public void deleteCourse(Long courseId) {
        courseCommandService.deleteCourse(courseId);
    }

    @Override
    public void updateCourseDuration(Long courseId) {
        courseCommandService.updateCourseDuration(courseId);
    }
}
