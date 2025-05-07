package com.logicerror.e_learning.services.course.operationhandlers.update;

import com.logicerror.e_learning.entities.course.Course;
import com.logicerror.e_learning.repositories.CourseRepository;
import com.logicerror.e_learning.services.course.CourseUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseUpdateHandler extends BaseCourseUpdateHandler{
    private final CourseUpdateService courseUpdateService;
    private final CourseRepository courseRepository;


    @Override
    protected void processRequest(CourseUpdateContext context) {
        logger.debug("Updating course with ID: {}", context.getCourseId());
        courseUpdateService.update(context.getExistingCourse(), context.getRequest());
        Course updatedCourse = courseRepository.save(context.getExistingCourse());
        context.setUpdatedCourse(updatedCourse);
        logger.debug("Successfully updated course with ID: {}", updatedCourse.getId());
    }
}
