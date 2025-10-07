package com.logicerror.e_learning.courses.services.operationhandlers.update;

import com.logicerror.e_learning.courses.entities.Course;
import com.logicerror.e_learning.courses.exceptions.CourseNotFoundException;
import com.logicerror.e_learning.courses.repositories.CourseRepository;
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
