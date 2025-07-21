package com.logicerror.e_learning.services.video.operationhandlers.create;

import com.logicerror.e_learning.exceptions.video.VideoTitleAlreadyExistsException;
import com.logicerror.e_learning.repositories.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VideoCreateValidationHandler extends BaseVideoCreationHandler {
    private final VideoRepository videoRepository;


    @Override
    protected void processRequest(VideoCreationContext context) {
        logger.debug("Validating video creation for user: {}", context.getUser().getUsername());
        doVideoExistsCheck(context.getRequest().getTitle(), context.getCourseId());
        logger.debug("Video title '{}' does not exist in course with ID: {}", context.getRequest().getTitle(), context.getCourseId());
    }

    private void doVideoExistsCheck(String title, Long courseId) {
        if(videoRepository.existsByTitleAndCourseId(title, courseId)) {
            throw new VideoTitleAlreadyExistsException("Video with title '" + title + "' already exists in course with ID: " + courseId);
        }
    }
}
