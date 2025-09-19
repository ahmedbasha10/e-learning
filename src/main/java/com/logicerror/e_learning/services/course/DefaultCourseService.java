package com.logicerror.e_learning.services.course;

import com.logicerror.e_learning.dto.CourseDto;
import com.logicerror.e_learning.dto.SectionDto;
import com.logicerror.e_learning.entities.course.Course;
import com.logicerror.e_learning.entities.course.Section;
import com.logicerror.e_learning.requests.course.CreateCourseRequest;
import com.logicerror.e_learning.requests.course.UpdateCourseRequest;
import com.logicerror.e_learning.services.section.SectionService;
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
    private final SectionService sectionService;

    // Query operations
    public Page<CourseDto> getAllCourses(Pageable pageable) {
        Page<Course> courses = courseQueryService.getAllCoursesWithStudentsCount(pageable);
        return courses.map(courseDtoService::convertToDto);
    }

    public CourseDto getCourseById(Long courseId) {
        Course course = courseQueryService.getCourseById(courseId);
        return courseDtoService.convertToDto(course);
    }

    public CourseDto getCourseByTitle(String title) {
        Course course = courseQueryService.getCourseByTitle(title);
        return courseDtoService.convertToDto(course);
    }

    public Page<CourseDto> getCoursesByCategory(String category, Pageable pageable) {
        Page<Course> courses = courseQueryService.getCoursesByCategory(category, pageable);
        return courses.map(courseDtoService::convertToDto);
    }

    public Page<CourseDto> getCoursesByLevel(String level, Pageable pageable) {
        Page<Course> courses = courseQueryService.getCoursesByLevel(level, pageable);
        return courses.map(courseDtoService::convertToDto);
    }

    public Page<CourseDto> getCoursesByTeacher(Pageable pageable) {
        Page<Course> courses = courseQueryService.getCoursesByAuthenticatedTeacher(pageable);
        return courses.map(courseDtoService::convertToDto);
    }

    public Page<SectionDto> getCourseSections(Long courseId, Pageable pageable) {
        Page<Section> sections = courseQueryService.getCourseSections(courseId, pageable);
        return sections.map(sectionService::convertToDto);
    }

    // Command operations
    @PreAuthorize("hasRole('TEACHER')")
    public CourseDto createCourse(CreateCourseRequest request, MultipartFile thumbnail) {
        Course course = courseCommandService.createCourse(request, thumbnail);
        return courseDtoService.convertToDto(course);
    }

    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public CourseDto updateCourse(Long courseId, UpdateCourseRequest request) {
        Course course = courseCommandService.updateCourse(courseId, request);
        return courseDtoService.convertToDto(course);
    }

    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public void deleteCourse(Long courseId) {
        courseCommandService.deleteCourse(courseId);
    }

    public void updateCourseDuration(Long courseId) {
        courseCommandService.updateCourseDuration(courseId);
    }

    // Utility operations
    @Override
    public boolean courseExists(Long courseId) {
        return courseQueryService.courseExists(courseId);
    }

    @Override
    public boolean courseExistsByTitle(String title) {
        return courseQueryService.courseExistsByTitle(title);
    }

    @Override
    public int getCourseStudentsCount(Long courseId) {
        return courseQueryService.getEnrolledStudentsCount(courseId);
    }
}
