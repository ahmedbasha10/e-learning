package com.logicerror.e_learning.services.video.operationhandlers.create;

import com.logicerror.e_learning.events.VideosModifiedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostVideoCreationHandler extends BaseVideoCreationHandler{

    private final ApplicationEventPublisher eventPublisher;

    @Override
    protected void processRequest(VideoCreationContext context) {
        logger.info("Post video creation handler started for video: {}", context.getVideo().getId());
        eventPublisher.publishEvent(new VideosModifiedEvent(this, context.getCourseId(), context.getSectionId()));
        logger.info("Post video creation handler completed for video: {}", context.getVideo().getId());
    }
}
