package com.logicerror.e_learning.videos.services.fields;

import com.logicerror.e_learning.videos.entities.Video;
import com.logicerror.e_learning.videos.exceptions.VideoTitleAlreadyExistsException;
import com.logicerror.e_learning.videos.repositories.VideoRepository;
import com.logicerror.e_learning.videos.requests.UpdateVideoRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class VideoTitleFieldUpdater implements VideoFieldUpdater {
    private final VideoRepository videoRepository;

    @Override
    public void updateField(Video entity, UpdateVideoRequest request) {
        log.debug("Updating video title for video ID: {}", entity.getId());
        if (request.getTitle() != null && !request.getTitle().isEmpty()) {
            log.debug("requested title update field is provided: {}", request.getTitle());
            if(videoRepository.existsByTitleAndCourseId(request.getTitle(), entity.getCourse().getId())) {
                throw new VideoTitleAlreadyExistsException("Video with the same title already exists in this course.");
            }
            entity.setTitle(request.getTitle());
        }
    }
}
