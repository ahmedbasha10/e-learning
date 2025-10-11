package com.logicerror.e_learning.services.video.operationhandlers.create;

import com.logicerror.e_learning.sections.entities.Section;
import com.logicerror.e_learning.entities.course.Video;
import com.logicerror.e_learning.sections.exceptions.SectionNotFoundException;
import com.logicerror.e_learning.mappers.VideoMapper;
import com.logicerror.e_learning.sections.repositories.SectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PreVideoCreationHandler extends BaseVideoCreationHandler {
    private final VideoMapper videoMapper;
    private final SectionRepository sectionRepository;

    @Override
    protected void processRequest(VideoCreationContext context) {
        logger.debug("Pre-video creation processing for user: {}", context.getUser().getUsername());

        Section section = findSectionOrThrow(context.getSectionId());
        context.setSection(section);
        Video video = videoMapper.createVideoRequestToVideo(context.getRequest(), section, section.getCourse());
        context.setVideo(video);

        logger.debug("Pre-video creation processing completed for user: {}", context.getUser().getUsername());
    }

    private Section findSectionOrThrow(Long sectionId) {
        return sectionRepository.findById(sectionId)
                .orElseThrow(() -> new SectionNotFoundException("Section not found with id: " + sectionId));
    }

}
