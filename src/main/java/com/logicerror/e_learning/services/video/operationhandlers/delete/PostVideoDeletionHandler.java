package com.logicerror.e_learning.services.video.operationhandlers.delete;

import com.logicerror.e_learning.events.VideosModifiedEvent;
import com.logicerror.e_learning.services.video.CourseProgressDomainService;
import com.logicerror.e_learning.services.video.models.VideoCompletionCleanupResult;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostVideoDeletionHandler extends BaseVideoDeletionHandler{
    private final ApplicationEventPublisher eventPublisher;
    private final CourseProgressDomainService courseProgressDomainService;

    @Override
    protected void processRequest(VideoDeletionContext context) {
        logger.info("Post video creation handler started for video: {}", context.getVideoId());
        eventPublisher.publishEvent(new VideosModifiedEvent(this, context.getTargetVideo().fetchCourseId(),
                context.getTargetVideo().fetchSectionId()));
        VideoCompletionCleanupResult cleanupResult = context.getVideoCompletionCleanupResult();
        if(cleanupResult.hasAffectedUsers()) {
            logger.info("Video completions removed for video ID: {}. Affected users: {}", context.getVideoId(), cleanupResult.getAffectedUserIds().size());
            courseProgressDomainService.updateCourseProgressForAllUsers(cleanupResult.getCourseId());
        } else {
            logger.debug("No video completions found for video ID: {}", context.getVideoId());
        }
        logger.info("Post video creation handler completed for video: {}", context.getVideoId());
    }
}
