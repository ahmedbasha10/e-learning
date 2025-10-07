package com.logicerror.e_learning.courses.services.operationhandlers.creation;

import com.logicerror.e_learning.courses.exceptions.CourseTitleAlreadyExistsException;
import com.logicerror.e_learning.courses.repositories.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ValidationHandler extends BaseCourseCreationHandler {
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
