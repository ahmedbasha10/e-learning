package com.logicerror.e_learning.services.video.operationhandlers.update;

import com.logicerror.e_learning.entities.course.Video;
import com.logicerror.e_learning.repositories.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VideoUpdateHandler extends BaseVideoUpdateHandler{
    private final VideoRepository videoRepository;

    @Override
    protected void processRequest(VideoUpdateContext context) {
        logger.debug("Processing video update for video: {}", context.getVideo().getId());
        Video updatedVideo = videoRepository.save(context.getVideo());
        context.setVideo(updatedVideo);
        logger.debug("Video update processed for video: {}", context.getVideo().getId());
    }
}
