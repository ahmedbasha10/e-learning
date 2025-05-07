package com.logicerror.e_learning.services.course.operationhandlers;

import com.logicerror.e_learning.exceptions.course.CourseTitleAlreadyExistsException;
import com.logicerror.e_learning.repositories.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ValidationHandler extends BaseCourseOperationHandler {
    private final CourseRepository courseRepository;

    @Override
    protected void processRequest(CourseCreationContext context) {
        logger.debug("Validating course creation request");
        if (courseRepository.existsByTitle(context.getRequest().getTitle())) {
            logger.error("Course creation failed: title already exists: {}", context.getRequest().getTitle());
            throw new CourseTitleAlreadyExistsException("Course already exists with title: " + context.getRequest().getTitle());
        }
        logger.debug("Course validation successful");
    }
    
}
