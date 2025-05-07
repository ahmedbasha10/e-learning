package com.logicerror.e_learning.services.course;

import com.logicerror.e_learning.dto.CourseDto;
import com.logicerror.e_learning.entities.course.Course;
import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.exceptions.course.CourseNotFoundException;
import com.logicerror.e_learning.mappers.CourseMapper;
import com.logicerror.e_learning.repositories.CourseRepository;
import com.logicerror.e_learning.repositories.TeacherCoursesRepository;
import com.logicerror.e_learning.requests.course.CreateCourseRequest;
import com.logicerror.e_learning.requests.course.UpdateCourseRequest;
import com.logicerror.e_learning.services.course.operationhandlers.CourseCreationContext;
import com.logicerror.e_learning.services.course.operationhandlers.CourseOperationChainBuilder;
import com.logicerror.e_learning.services.course.operationhandlers.CourseOperationHandler;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


@Service
@RequiredArgsConstructor
public class CourseService implements ICourseService {
    private final CourseRepository courseRepository;
    private final CourseUpdateService courseUpdateService;
    private final CourseMapper courseMapper;
    private final TeacherCoursesRepository teacherCoursesRepository;
    private final CourseOperationChainBuilder<CreateCourseRequest> courseOperationChainBuilder; // <CreateCourseRequest>
    private final Logger logger = LoggerFactory.getLogger(CourseService.class);


    @Override
    public CourseDto getCourseById(Long courseId) {
        Assert.notNull(courseId, "Course ID must not be null");
        logger.debug("Fetching course with ID: {}", courseId);
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> {
                    logger.error("Course not found with ID: {}", courseId);
                    return new CourseNotFoundException("Course not found with id: " + courseId);
                });
        return convertToDto(course);
    }

    @Override
    public CourseDto getCourseByTitle(String title) {
        Assert.notNull(title, "Course title must not be null");
        logger.debug("Fetching course with title: {}", title);
        Course course = courseRepository.findByTitle(title)
                .orElseThrow(() -> {
                    logger.error("Course not found with title: {}", title);
                    return new CourseNotFoundException("Course not found with title: " + title);
                });
        return convertToDto(course);
    }

    @Override
    public Page<CourseDto> getCoursesByCategory(String category, Pageable pageable) {
        Assert.notNull(category, "Course category must not be null");
        Assert.notNull(pageable, "Pageable must not be null");
        logger.debug("Fetching courses by category: {}, page: {}", category, pageable.getPageNumber());
        Page<Course> courses = courseRepository.findByCategory(category, pageable);
        logger.debug("Found {} courses in category: {}", courses.getTotalElements(), category);
        return courses.map(this::convertToDto);
    }

    @Override
    public Page<CourseDto> getCoursesByLevel(String level, Pageable pageable) {
        Assert.notNull(level, "Course level must not be null");
        Assert.notNull(pageable, "Pageable must not be null");
        logger.debug("Fetching courses by level: {}, page: {}", level, pageable.getPageNumber());
        Page<Course> courses = courseRepository.findByLevel(level, pageable);
        logger.debug("Found {} courses with level: {}", courses.getTotalElements(), level);
        return courses.map(this::convertToDto);
    }

    @Override
    @Transactional
    public CourseDto createCourse(CreateCourseRequest request) throws AccessDeniedException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CourseOperationHandler courseOperationHandler = courseOperationChainBuilder.build();
        CourseCreationContext context = new CourseCreationContext(request, user);
        courseOperationHandler.handle(context);
        return convertToDto(context.getCourse());
    }


    private static void authorizeUser(String message) throws AccessDeniedException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user.getAuthorities().stream().noneMatch(authority ->
                authority.getAuthority().equals("ROLE_TEACHER") || authority.getAuthority().equals("ROLE_ADMIN"))) {
            throw new AccessDeniedException(message);
        }
    }

    @Override
    @Transactional
    public CourseDto updateCourse(Long courseId, UpdateCourseRequest request) throws AccessDeniedException {
        authorizeUser("User does not have permission to update a course");
        logger.debug("Updating course with ID: {}", courseId);
        Course existingCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> {
                    logger.error("Course not found with ID: {}", courseId);
                    return new CourseNotFoundException("Course not found with id: " + courseId);
                });

        courseUpdateService.update(existingCourse, request);
        Course updatedCourse = courseRepository.save(existingCourse);
        logger.info("Successfully updated course with ID: {}", updatedCourse.getId());
        return convertToDto(updatedCourse);
    }

    @Override
    @Transactional
    public void deleteCourse(Long courseId) {
        authorizeUser("User does not have permission to delete a course");
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> {
                    logger.error("Course not found with ID: {}", courseId);
                    return new CourseNotFoundException("Course not found with id: " + courseId);
                });
        courseRepository.delete(course);
        logger.info("Successfully deleted course with ID: {}", courseId);
    }

    @Override
    public CourseDto convertToDto(Course course) {
        return courseMapper.courseToCourseDto(course);
    }
}
