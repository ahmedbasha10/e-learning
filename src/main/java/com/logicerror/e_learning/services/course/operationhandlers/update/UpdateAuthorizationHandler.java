package com.logicerror.e_learning.services.course.operationhandlers.update;

import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.services.authorization.CourseAuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateAuthorizationHandler extends BaseCourseUpdateHandler{
    private final CourseAuthorizationService courseAuthorizationService;

    @Override
    protected void processRequest(CourseUpdateContext context) {
        logger.debug("Checking authorization for course update");
        User user = context.getUser();
        if(!courseAuthorizationService.hasAccessToModifyCourse(context.getCourseId(), user)) {
            logger.error("User does not have permission to update the course with ID: {}", context.getCourseId());
            throw new AccessDeniedException("You do not have permission to update this course.");
        }
        logger.debug("User has permission to update the course");
    }
}
