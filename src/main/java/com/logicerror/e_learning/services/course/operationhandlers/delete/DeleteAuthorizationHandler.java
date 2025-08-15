package com.logicerror.e_learning.services.course.operationhandlers.delete;

import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.services.authorization.CourseAuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteAuthorizationHandler extends BaseCourseDeleteHandler{
    private final CourseAuthorizationService courseAuthorizationService;

    @Override
    protected void processRequest(CourseDeleteContext context) {
        logger.debug("Checking authorization for course deletion");
        User user = context.getUser();
        if(!courseAuthorizationService.hasAccessToModifyCourse(context.getCourseId(), user)) {
            logger.error("User does not have permission to delete the course with ID: {}", context.getCourseId());
            throw new AccessDeniedException("You do not have permission to delete this course.");
        }

        logger.debug("User has permission to delete the course");
    }
}
