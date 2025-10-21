package com.logicerror.e_learning.videos.services.operationhandlers.create;

import com.logicerror.e_learning.videos.entities.Video;
import com.logicerror.e_learning.videos.exceptions.VideoCreationFailedException;
import com.logicerror.e_learning.videos.repositories.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VideoCreationHandler extends BaseVideoCreationHandler {
    private final VideoRepository videoRepository;

    @Override
    protected void processRequest(VideoCreationContext context) {
        logger.debug("Processing video creation for user: {}", context.getUser().getUsername());

        Video savedVideo = videoRepository.save(context.getVideo());
        if(savedVideo.getId() == null || savedVideo.getId() <= 0) {
            logger.error("Failed to save video for user: {}", context.getUser().getUsername());
            throw new VideoCreationFailedException("Video creation failed, unable to save video.");
        }
        context.setVideo(savedVideo);

        logger.info("Video creation request processed for title: {}", context.getRequest().getTitle());
    }
}
