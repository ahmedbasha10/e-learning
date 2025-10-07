package com.logicerror.e_learning.services.video.operationhandlers.delete;

import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.courses.security.CourseAuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VideoDeletionAuthorizationHandler extends BaseVideoDeletionHandler {
    private final CourseAuthorizationService courseAuthorizationService;

    @Override
    protected void processRequest(VideoDeletionContext context) {
        logger.debug("Processing video update authorization for user {} and video id {}",
                context.getUser().getUsername(), context.getVideoId());
        User user = context.getUser();

        boolean isAuthorized = courseAuthorizationService.hasAccessToModifyCourse(context.getTargetVideo().fetchCourseId(), user);

        if(!isAuthorized) {
            logger.error("User {} is not authorized to delete video with id: {}", user.getUsername(), context.getVideoId());
            throw new AccessDeniedException(String.format("User is not authorized to delete video with id: %s", context.getVideoId()));
        }

        logger.debug("User {} has permission to update a video with id: {}", user.getUsername(), context.getVideoId());
    }
}
