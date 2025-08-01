package com.logicerror.e_learning.services.video.operationhandlers.delete;

import com.logicerror.e_learning.services.video.CourseProgressDomainService;
import com.logicerror.e_learning.services.video.models.VideoCompletionCleanupResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PreVideoDeletionHandler extends BaseVideoDeletionHandler {
    private final CourseProgressDomainService courseProgressDomainService;

    @Override
    protected void processRequest(VideoDeletionContext context) {
        logger.debug("Pre-video deletion handler processing for video ID: {}", context.getVideoId());
        VideoCompletionCleanupResult cleanupResult = courseProgressDomainService.removeVideoCompletions(context.getVideoId());
        context.setVideoCompletionCleanupResult(cleanupResult);
        logger.debug("finished pre-video deletion handler processing for video ID: {}", context.getVideoId());
    }
}
