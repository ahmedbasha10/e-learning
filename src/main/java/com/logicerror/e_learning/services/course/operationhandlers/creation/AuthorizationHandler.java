package com.logicerror.e_learning.services.course.operationhandlers.creation;

import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.services.authorization.CourseAuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorizationHandler extends BaseCourseCreationHandler {
    private final CourseAuthorizationService courseAuthorizationService;

    @Override
    protected void processRequest(CourseCreationContext context) {
        logger.debug("Checking authorization for course creation");
        User user = context.getUser();
        if (!courseAuthorizationService.hasAccessToCreateCourse(user)) {
            logger.error("User does not have permission to create a course");
            throw new AccessDeniedException("User does not have permission to create a course");
        }
        logger.debug("User has permission to create a course");
    }
    
}
