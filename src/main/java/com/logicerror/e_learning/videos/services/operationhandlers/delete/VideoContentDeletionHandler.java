package com.logicerror.e_learning.videos.services.operationhandlers.delete;

import com.logicerror.e_learning.services.FileManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VideoContentDeletionHandler extends BaseVideoDeletionHandler{
    private final FileManagementService fileManagementService;

    @Override
    protected void processRequest(VideoDeletionContext context) {
        logger.debug("Processing video content deletion for video id: {}", context.getVideoId());
        fileManagementService.deleteFile(context.getTargetVideo().getUrl());
        logger.info("Video content deleted successfully for video id: {}", context.getVideoId());
    }
}
