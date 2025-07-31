package com.logicerror.e_learning.services.video.operationhandlers.update;

import com.logicerror.e_learning.entities.course.Video;
import com.logicerror.e_learning.exceptions.video.VideoNotFoundException;
import com.logicerror.e_learning.repositories.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VideoUpdateInitializationHandler extends BaseVideoUpdateHandler{
    private final VideoRepository videoRepository;

    @Override
    protected void processRequest(VideoUpdateContext context) {
        logger.debug("Initializing video update for video with ID: {}", context.getVideoId());
        Video video = videoRepository.findById(context.getVideoId())
                .orElseThrow(() -> new VideoNotFoundException("Video not found with id: " + context.getVideoId()));
        context.setVideo(video);
        logger.debug("Video update initialized for video with ID: {}", context.getVideoId());
    }
}
