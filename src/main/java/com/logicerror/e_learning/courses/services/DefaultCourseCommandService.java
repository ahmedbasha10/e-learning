package com.logicerror.e_learning.courses.services;

import com.logicerror.e_learning.courses.entities.Course;
import com.logicerror.e_learning.sections.entities.Section;
import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.courses.exceptions.CourseNotFoundException;
import com.logicerror.e_learning.courses.repositories.CourseRepository;
import com.logicerror.e_learning.sections.repositories.SectionRepository;
import com.logicerror.e_learning.courses.requests.CreateCourseRequest;
import com.logicerror.e_learning.courses.requests.UpdateCourseRequest;
import com.logicerror.e_learning.services.OperationHandler;
import com.logicerror.e_learning.courses.services.operationhandlers.creation.CourseCreationChainBuilder;
import com.logicerror.e_learning.courses.services.operationhandlers.creation.CourseCreationContext;
import com.logicerror.e_learning.courses.services.operationhandlers.delete.CourseDeleteChainBuilder;
import com.logicerror.e_learning.courses.services.operationhandlers.delete.CourseDeleteContext;
import com.logicerror.e_learning.courses.services.operationhandlers.update.CourseUpdateChainBuilder;
import com.logicerror.e_learning.courses.services.operationhandlers.update.CourseUpdateContext;
import com.logicerror.e_learning.services.user.IUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service responsible for course command operations (create, update, delete)
 * Separated from query operations to follow CQRS pattern
 */
@Service
@RequiredArgsConstructor
public class DefaultCourseCommandService implements CourseCommandService {
    
    private final IUserService userService;
    private final CourseRepository courseRepository;
    private final SectionRepository sectionRepository;
    private final CourseCreationChainBuilder courseCreationChainBuilder;
    private final CourseUpdateChainBuilder courseUpdateChainBuilder;
    private final CourseDeleteChainBuilder courseDeleteChainBuilder;
    private final Logger logger = LoggerFactory.getLogger(DefaultCourseCommandService.class);

    @Override
    @Transactional
    @PreAuthorize("hasRole('TEACHER')")
    public Course createCourse(CreateCourseRequest request, MultipartFile thumbnail) {
        User user = userService.getAuthenticatedUser();
        OperationHandler<CourseCreationContext> courseOperationHandler = courseCreationChainBuilder.build();
        CourseCreationContext context = new CourseCreationContext(request, thumbnail, user);
        courseOperationHandler.handle(context);
        return context.getCourse();
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public Course updateCourse(Long courseId, UpdateCourseRequest request) {
        User user = userService.getAuthenticatedUser();
        OperationHandler<CourseUpdateContext> courseOperationHandler = courseUpdateChainBuilder.build();
        CourseUpdateContext context = new CourseUpdateContext(courseId, request, user);
        courseOperationHandler.handle(context);
        return context.getUpdatedCourse();
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public void deleteCourse(Long courseId) {
        User user = userService.getAuthenticatedUser();
        OperationHandler<CourseDeleteContext> courseOperationHandler = courseDeleteChainBuilder.build();
        CourseDeleteContext context = new CourseDeleteContext(courseId, user);
        courseOperationHandler.handle(context);
    }

    @Override
    @Transactional
    public void updateCourseDuration(Long courseId) {
        Assert.notNull(courseId, "Course must not be null");
        logger.debug("Updating duration for course with ID: {}", courseId);
        
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + courseId));
        
        Long totalDuration = sectionRepository.findAllByCourseId(courseId)
                .stream()
                .mapToLong(Section::getDuration)
                .sum();
        
        course.setDuration(totalDuration.intValue());
        logger.debug("Updated duration for course ID: {} to {}", course.getId(), totalDuration);
        courseRepository.save(course);
    }
}
