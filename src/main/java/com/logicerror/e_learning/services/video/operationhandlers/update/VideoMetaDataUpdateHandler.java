package com.logicerror.e_learning.services.video.operationhandlers.update;

import com.logicerror.e_learning.services.video.fields.VideoFieldsUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VideoMetaDataUpdateHandler extends BaseVideoUpdateHandler{
    private final VideoFieldsUpdateService videoFieldsUpdateService;

    @Override
    protected void processRequest(VideoUpdateContext context) {
        logger.debug("Updating video metadata for video with ID: {}", context.getVideoId());
        videoFieldsUpdateService.update(context.getVideo(), context.getRequest());
        logger.debug("Video metadata updated for video with ID: {}", context.getVideoId());
    }
}
