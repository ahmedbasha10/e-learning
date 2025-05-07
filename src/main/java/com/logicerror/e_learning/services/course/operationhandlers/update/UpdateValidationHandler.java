package com.logicerror.e_learning.services.course.operationhandlers.update;

import com.logicerror.e_learning.entities.course.Course;
import com.logicerror.e_learning.exceptions.course.CourseNotFoundException;
import com.logicerror.e_learning.repositories.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateValidationHandler extends BaseCourseUpdateHandler{
    private final CourseRepository courseRepository;

    @Override
    protected void processRequest(CourseUpdateContext context) {
        logger.debug("Validating course update request");
        Course existingCourse = courseRepository.findById(context.getCourseId())
                .orElseThrow(() -> new CourseNotFoundException("Course not found with ID: " + context.getCourseId()));
        context.setExistingCourse(existingCourse);
        logger.debug("Course validation successful");
    }
}
