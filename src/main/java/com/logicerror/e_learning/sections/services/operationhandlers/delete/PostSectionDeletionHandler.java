package com.logicerror.e_learning.sections.services.operationhandlers.delete;

import com.logicerror.e_learning.constants.DeletionStatus;
import com.logicerror.e_learning.entities.course.FileDeletionQueue;
import com.logicerror.e_learning.entities.course.Video;
import com.logicerror.e_learning.events.SectionRemovedEvent;
import com.logicerror.e_learning.repositories.FileDeletionQueueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class PostSectionDeletionHandler extends BaseSectionDeletionHandler{
    private final ApplicationEventPublisher eventPublisher;
    private final FileDeletionQueueRepository fileDeletionQueueRepository;

    @Override
    protected void processRequest(SectionDeletionContext context) {
        logger.info("Post section deletion handler started for section: {}", context.getSectionId());
        eventPublisher.publishEvent(new SectionRemovedEvent(this, context.getTargetSection().getCourse().getId()));
        deleteSectionContent(context);
        logger.info("Post section deletion handler completed for section: {}", context.getSectionId());
    }

    private void deleteSectionContent(SectionDeletionContext context) {
        logger.info("Deleting content for section: {}", context.getTargetSection().getTitle());
        Set<Video> videos = context.getTargetSection().getVideos();
        List<FileDeletionQueue> deletionQueue = videos.stream()
                .map(video -> new FileDeletionQueue(video.getUrl(), 0, DeletionStatus.PENDING))
                .toList();

        if (!deletionQueue.isEmpty()) {
            logger.debug("Adding {} videos to deletion queue for section with ID: {}", deletionQueue.size(), context.getSectionId());
            fileDeletionQueueRepository.saveAll(deletionQueue);
        } else {
            logger.debug("No videos found for section with ID: {}", context.getSectionId());
        }
    }
}
