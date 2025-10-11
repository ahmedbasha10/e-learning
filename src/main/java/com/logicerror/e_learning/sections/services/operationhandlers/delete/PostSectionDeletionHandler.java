package com.logicerror.e_learning.sections.services.operationhandlers.delete;

import com.logicerror.e_learning.entities.course.Video;
import com.logicerror.e_learning.events.SectionRemovedEvent;
import com.logicerror.e_learning.services.FileManagementService;
import com.logicerror.e_learning.services.video.CourseProgressDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostSectionDeletionHandler extends BaseSectionDeletionHandler{
    private final ApplicationEventPublisher eventPublisher;
    private final CourseProgressDomainService courseProgressDomainService;
    private final FileManagementService fileManagementService;

    @Override
    protected void processRequest(SectionDeletionContext context) {
        logger.info("Post section deletion handler started for section: {}", context.getSectionId());
        eventPublisher.publishEvent(new SectionRemovedEvent(this, context.getTargetSection().getCourse().getId()));
        courseProgressDomainService.updateCourseProgressForAllUsers(context.getTargetSection().getCourse().getId());
        deleteSectionContent(context);
        logger.info("Post section deletion handler completed for section: {}", context.getSectionId());
    }

    private void deleteSectionContent(SectionDeletionContext context) {
        // Logic to delete section content goes here
        logger.info("Deleting content for section: {}", context.getTargetSection().getTitle());
        // Example: contentRepository.deleteBySectionId(context.getSectionId());
        for(Video video : context.getTargetSection().getVideos()) {
            logger.info("Deleting video: {}", video.getTitle());
            fileManagementService.deleteFile(video.getUrl());
        }
    }
}
