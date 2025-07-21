package com.logicerror.e_learning.services.video.operationhandlers.create;

import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.services.authorization.CourseAuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VideoCreateAuthorizationHandler extends BaseVideoCreationHandler{

    private final CourseAuthorizationService courseAuthorizationService;

    @Override
    protected void processRequest(VideoCreationContext context) {
        logger.debug("Processing video creation authorization for user: {}", context.getUser().getUsername());
        User user = context.getUser();
        if (user == null) {
            logger.error("User is null, cannot authorize video creation.");
            throw new IllegalArgumentException("User must be authenticated to create a video.");
        }

        if (!user.isTeacher()) {
            logger.error("User {} is not authorized to create a video.", user.getUsername());
            throw new AccessDeniedException("User is not authorized to create a video.");
        }

        boolean isOwner = courseAuthorizationService.isCourseOwner(context.getCourseId(), user.getId());

        if (!isOwner) {
            logger.error("User {} is not the owner of the course with ID {}.", user.getUsername(), context.getCourseId());
            throw new AccessDeniedException("User is not the owner of the course.");
        }

        logger.debug("User {} has permission to create a video in course with ID {}.", user.getUsername(), context.getCourseId());
    }
}
