package com.logicerror.e_learning.services.course;

import com.logicerror.e_learning.dto.CourseDto;
import com.logicerror.e_learning.dto.UserDto;
import com.logicerror.e_learning.entities.course.Course;
import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.exceptions.course.CourseNotFoundException;
import com.logicerror.e_learning.mappers.CourseMapper;
import com.logicerror.e_learning.mappers.UserMapper;
import com.logicerror.e_learning.repositories.CourseRepository;
import com.logicerror.e_learning.repositories.TeacherCoursesRepository;
import com.logicerror.e_learning.requests.course.CreateCourseRequest;
import com.logicerror.e_learning.requests.course.UpdateCourseRequest;
import com.logicerror.e_learning.services.OperationHandler;
import com.logicerror.e_learning.services.course.operationhandlers.creation.CourseCreationChainBuilder;
import com.logicerror.e_learning.services.course.operationhandlers.creation.CourseCreationContext;
import com.logicerror.e_learning.services.course.operationhandlers.delete.CourseDeleteChainBuilder;
import com.logicerror.e_learning.services.course.operationhandlers.delete.CourseDeleteContext;
import com.logicerror.e_learning.services.course.operationhandlers.update.CourseUpdateChainBuilder;
import com.logicerror.e_learning.services.course.operationhandlers.update.CourseUpdateContext;
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
    private final TeacherCoursesRepository teacherCoursesRepository;
    private final UserMapper userMapper;
    private final CourseMapper courseMapper;
    private final CourseCreationChainBuilder courseOperationChainBuilder; // <CreateCourseRequest>
    private final CourseUpdateChainBuilder courseUpdateChainBuilder; // <UpdateCourseRequest>
    private final CourseDeleteChainBuilder courseDeleteChainBuilder; // <DeleteCourseRequest>
    private final Logger logger = LoggerFactory.getLogger(CourseService.class);


    @Override
    public Course getCourseById(Long courseId) {
        Assert.notNull(courseId, "Course ID must not be null");
        logger.debug("Fetching course with ID: {}", courseId);
        return courseRepository.findById(courseId)
                .orElseThrow(() -> {
                    logger.error("Course not found with ID: {}", courseId);
                    return new CourseNotFoundException("Course not found with id: " + courseId);
                });
    }

    @Override
    public Course getCourseByTitle(String title) {
        Assert.notNull(title, "Course title must not be null");
        logger.debug("Fetching course with title: {}", title);
        return courseRepository.findByTitle(title)
                .orElseThrow(() -> {
                    logger.error("Course not found with title: {}", title);
                    return new CourseNotFoundException("Course not found with title: " + title);
                });
    }

    @Override
    public Page<Course> getAllCourses(Pageable pageable) {
        Assert.notNull(pageable, "Pageable must not be null");
        logger.debug("Fetching all courses, page: {}", pageable.getPageNumber());
        Page<Course> courses = courseRepository.findAll(pageable);
        logger.debug("Found {} courses", courses.getTotalElements());
        return courses;
    }

    @Override
    public Page<Course> getCoursesByCategory(String category, Pageable pageable) {
        Assert.notNull(category, "Course category must not be null");
        Assert.notNull(pageable, "Pageable must not be null");
        logger.debug("Fetching courses by category: {}, page: {}", category, pageable.getPageNumber());
        Page<Course> courses = courseRepository.findByCategory(category, pageable);
        logger.debug("Found {} courses in category: {}", courses.getTotalElements(), category);
        return courses;
    }

    @Override
    public Page<Course> getCoursesByLevel(String level, Pageable pageable) {
        Assert.notNull(level, "Course level must not be null");
        Assert.notNull(pageable, "Pageable must not be null");
        logger.debug("Fetching courses by level: {}, page: {}", level, pageable.getPageNumber());
        Page<Course> courses = courseRepository.findByLevel(level, pageable);
        logger.debug("Found {} courses with level: {}", courses.getTotalElements(), level);
        return courses;
    }

    @Override
    @Transactional
    public Course createCourse(CreateCourseRequest request) throws AccessDeniedException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        OperationHandler<CourseCreationContext> courseOperationHandler = courseOperationChainBuilder.build();
        CourseCreationContext context = new CourseCreationContext(request, user);
        courseOperationHandler.handle(context);
        return context.getCourse();
    }


    @Override
    @Transactional
    public Course updateCourse(Long courseId, UpdateCourseRequest request) throws AccessDeniedException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        OperationHandler<CourseUpdateContext> courseOperationHandler = courseUpdateChainBuilder.build();
        CourseUpdateContext context = new CourseUpdateContext(courseId, request, user);
        courseOperationHandler.handle(context);
        return context.getUpdatedCourse();
    }

    @Override
    @Transactional
    public void deleteCourse(Long courseId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        OperationHandler<CourseDeleteContext> courseOperationHandler = courseDeleteChainBuilder.build();
        CourseDeleteContext context = new CourseDeleteContext(courseId, user);
        courseOperationHandler.handle(context);
    }

    @Override
    public CourseDto convertToDto(Course course) {
        User user = teacherCoursesRepository.findByCourseId(course.getId()).getFirst().getUser();
        UserDto teacher = userMapper.userToUserDto(user);
        return courseMapper.courseToCourseDto(course, teacher);
    }
}
