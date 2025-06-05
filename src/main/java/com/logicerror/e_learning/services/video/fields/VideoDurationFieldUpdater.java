package com.logicerror.e_learning.services.video.fields;

import com.logicerror.e_learning.entities.course.Video;
import com.logicerror.e_learning.requests.course.video.UpdateVideoRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class VideoDurationFieldUpdater implements VideoFieldUpdater {
    @Override
    public void updateField(Video entity, UpdateVideoRequest request) {
        log.debug("Updating video duration for video ID: {}", entity.getId());
        if(request.getDuration() == null) return;

        if (request.getDuration() <= 0) {
            log.error("Invalid video duration: {}", request.getDuration());
            throw new IllegalArgumentException("Video duration cannot be negative");
        }

        entity.setDuration(request.getDuration());
        log.debug("Video duration updated to: {} seconds for video ID: {}", request.getDuration(), entity.getId());
    }
}
