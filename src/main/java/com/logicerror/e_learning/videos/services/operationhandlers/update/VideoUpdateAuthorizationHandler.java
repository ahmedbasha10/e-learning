package com.logicerror.e_learning.videos.services.operationhandlers.update;

import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.courses.security.CourseAuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VideoUpdateAuthorizationHandler extends BaseVideoUpdateHandler{
    private final CourseAuthorizationService courseAuthorizationService;

    @Override
    protected void processRequest(VideoUpdateContext context) {
        logger.debug("Processing video update authorization for user {} and video id {}",
                context.getUser().getUsername(), context.getVideoId());
        User user = context.getUser();

        if (user == null) {
            logger.error("User is null, cannot authorize video update with id: {}", context.getVideoId());
            throw new AccessDeniedException("User must be authenticated to update a video.");
        }

        if (!user.isTeacher() && !user.isAdmin()) {
            logger.error("User is not authorized to update a video with id: {}", context.getVideoId());
            throw new AccessDeniedException("User is not authorized to update a video with id: " + context.getVideoId());
        }

        boolean isOwner = courseAuthorizationService.isCourseOwner(context.getVideo().fetchCourseId(), user.getId());

        if (!isOwner) {
            logger.error("User is not the owner of the course for this video with id: {}", context.getVideoId());
            throw new AccessDeniedException("User is not the owner of the course for this video with id: " + context.getVideoId() + ".");
        }

        logger.debug("User {} has permission to update a video with id: {}", user.getUsername(), context.getVideoId());
    }
}

