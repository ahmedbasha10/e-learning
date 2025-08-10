package com.logicerror.e_learning.services.section.operationhandlers.delete;

import com.logicerror.e_learning.entities.course.Video;
import com.logicerror.e_learning.services.video.CourseProgressDomainService;
import com.logicerror.e_learning.services.video.models.VideoCompletionCleanupResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PreSectionDeletionHandler extends BaseSectionDeletionHandler{
    private final CourseProgressDomainService courseProgressDomainService;

    @Override
    protected void processRequest(SectionDeletionContext context) {
        logger.info("Pre section deletion handler started for section: {}", context.getSectionId());
        List<Video> videos = context.getTargetSection().getVideos();
        for (Video video : videos) {
            logger.debug("Removing completions for video: {} for section: {}", video.getId(), context.getSectionId());
            VideoCompletionCleanupResult result = courseProgressDomainService.removeVideoCompletions(video.getId());
            context.addVideoCompletionCleanupResult(result);
        }
        logger.info("Pre section deletion handler completed for section: {}", context.getSectionId());
    }
}
