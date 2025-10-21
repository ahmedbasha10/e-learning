package com.logicerror.e_learning.videos.services.operationhandlers.delete;

import com.logicerror.e_learning.videos.repositories.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VideoDeletionHandler extends BaseVideoDeletionHandler{
    private final VideoRepository videoRepository;

    @Override
    protected void processRequest(VideoDeletionContext context) {
        logger.info("Deleting video with ID: {}", context.getVideoId());
        videoRepository.deleteById(context.getVideoId());
        logger.info("Video with ID: {} deleted successfully", context.getVideoId());
    }
}
