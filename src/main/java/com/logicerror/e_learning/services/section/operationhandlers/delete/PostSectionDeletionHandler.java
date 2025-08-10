package com.logicerror.e_learning.services.section.operationhandlers.delete;

import com.logicerror.e_learning.events.SectionRemovedEvent;
import com.logicerror.e_learning.services.video.CourseProgressDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostSectionDeletionHandler extends BaseSectionDeletionHandler{
    private final ApplicationEventPublisher eventPublisher;
    private final CourseProgressDomainService courseProgressDomainService;

    @Override
    protected void processRequest(SectionDeletionContext context) {
        logger.info("Post section deletion handler started for section: {}", context.getSectionId());
        eventPublisher.publishEvent(new SectionRemovedEvent(this, context.getTargetSection().getCourse().getId()));
        courseProgressDomainService.updateCourseProgressForAllUsers(context.getTargetSection().getCourse().getId());
        logger.info("Post section deletion handler completed for section: {}", context.getSectionId());
    }
}
