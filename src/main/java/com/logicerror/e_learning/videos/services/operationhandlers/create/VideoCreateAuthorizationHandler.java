package com.logicerror.e_learning.videos.services.operationhandlers.create;

import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.courses.security.CourseAuthorizationService;
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

        if(!courseAuthorizationService.hasAccessToModifyCourse(context.getCourseId(), user)) {
            logger.warn("User {} does not have permission to create a video in course with ID {}.", user.getUsername(), context.getCourseId());
            throw new AccessDeniedException("User does not have permission to create video in this course.");
        }

        logger.debug("User {} has permission to create a video in course with ID {}.", user.getUsername(), context.getCourseId());
    }
}
