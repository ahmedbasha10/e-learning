package com.logicerror.e_learning.services.video.operationhandlers.delete;

import com.logicerror.e_learning.entities.course.Video;
import com.logicerror.e_learning.exceptions.video.VideoNotFoundException;
import com.logicerror.e_learning.repositories.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VideoDeletionInitializationHandler extends BaseVideoDeletionHandler{
    private final VideoRepository videoRepository;

    @Override
    protected void processRequest(VideoDeletionContext context) {
        logger.info("Initializing video deletion for video ID: {}", context.getVideoId());
        Video video = videoRepository.findById(context.getVideoId())
                .orElseThrow(() -> new VideoNotFoundException("Video not found with ID: " + context.getVideoId()));
        context.setTargetVideo(video);
        logger.info("Video found: {}", video.getTitle());
    }
}
