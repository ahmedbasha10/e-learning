package com.logicerror.e_learning.services.video.operationhandlers.delete;

import com.logicerror.e_learning.events.VideosModifiedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostVideoDeletionHandler extends BaseVideoDeletionHandler{
    private final ApplicationEventPublisher eventPublisher;

    @Override
    protected void processRequest(VideoDeletionContext context) {
        logger.info("Post video creation handler started for video: {}", context.getVideoId());
        eventPublisher.publishEvent(new VideosModifiedEvent(this, context.getTargetVideo().fetchCourseId(),
                context.getTargetVideo().fetchSectionId()));
        logger.info("Post video creation handler completed for video: {}", context.getVideoId());
    }
}
