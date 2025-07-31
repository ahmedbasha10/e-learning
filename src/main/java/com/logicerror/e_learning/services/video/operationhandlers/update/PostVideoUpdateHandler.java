package com.logicerror.e_learning.services.video.operationhandlers.update;

import com.logicerror.e_learning.events.VideosModifiedEvent;
import com.logicerror.e_learning.repositories.VideoRepository;
import com.logicerror.e_learning.services.FileManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostVideoUpdateHandler extends BaseVideoUpdateHandler{
    private final ApplicationEventPublisher eventPublisher;
    private final FileManagementService fileManagementService;
    private final VideoRepository videoRepository;

    @Override
    protected void processRequest(VideoUpdateContext context) {
        logger.info("Post video update handler started for video: {}", context.getVideo().getId());
        if(context.getVideoFile() == null) {
            logger.info("Stopping post video update handler, video content not updated for video: {}",
                    context.getVideo().getId());
            updateVideoParentDirectoryTitle(context);
            return;
        }
        deleteOldVideoFile(context);
        eventPublisher.publishEvent(new VideosModifiedEvent(this, context.getVideo().fetchCourseId(),
                context.getVideo().fetchSectionId()));
        logger.info("Post video update handler completed for video: {}", context.getVideo().getId());
    }

    private void updateVideoParentDirectoryTitle(VideoUpdateContext context) {
        logger.debug("Updating parent directory title for video: {}", context.getVideo().getId());
        String newTitle = context.getVideo().getTitle();
        String oldFilePath = context.getVideo().getUrl();
        String newFilePath = fileManagementService.updateParentDirectoryTitle(oldFilePath, newTitle);
        context.getVideo().setUrl(newFilePath);
        videoRepository.save(context.getVideo());
        logger.debug("Updated parent directory title to '{}' for video: {}", newTitle, context.getVideo().getId());
    }

    private void deleteOldVideoFile(VideoUpdateContext context) {
        logger.debug("Deleting old video file: {}", context.getVideo().getUrl());
        String oldFilePath = context.getOldVideoFilePath();
        fileManagementService.deleteFile(oldFilePath);
        logger.debug("Deleted old video file: {}", oldFilePath);
    }
}
