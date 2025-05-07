package com.logicerror.e_learning.services.course.operationhandlers.creation;

import com.logicerror.e_learning.entities.course.Course;
import com.logicerror.e_learning.exceptions.course.CourseCreationFailedException;
import com.logicerror.e_learning.mappers.CourseMapper;
import com.logicerror.e_learning.repositories.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseCreationHandler extends BaseCourseCreationHandler {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    
    
    @Override
    protected void processRequest(CourseCreationContext context) {
        logger.info("Creating new course with title: {}", context.getRequest().getTitle());
        Course course = courseMapper.createCourseRequestToCourse(context.getRequest());
        Course savedCourse = courseRepository.save(course);
        
        if (savedCourse.getId() == null) {
            logger.error("Course creation failed: could not generate ID");
            throw new CourseCreationFailedException("Failed to create course");
        }
        context.setCourse(savedCourse);
        logger.info("Successfully created course with ID: {}", savedCourse.getId());
    }

    

}
