package com.logicerror.e_learning.services.video.fields;

import com.logicerror.e_learning.entities.course.Video;
import com.logicerror.e_learning.requests.course.video.UpdateVideoRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class VideoUrlFieldUpdater implements VideoFieldUpdater {
    @Override
    public void updateField(Video entity, UpdateVideoRequest request) {
        log.debug("Updating video URL for video ID: {}", entity.getId());
        if (request.getUrl() != null && !request.getUrl().isEmpty()) {
            log.info("Video URL update field from {} to {}", entity.getUrl(), request.getUrl());
            entity.setUrl(request.getUrl());
        }
    }
}
