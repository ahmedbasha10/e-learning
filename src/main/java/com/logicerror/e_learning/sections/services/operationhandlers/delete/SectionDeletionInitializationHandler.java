package com.logicerror.e_learning.sections.services.operationhandlers.delete;

import com.logicerror.e_learning.sections.entities.Section;
import com.logicerror.e_learning.sections.exceptions.SectionNotFoundException;
import com.logicerror.e_learning.sections.repositories.SectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SectionDeletionInitializationHandler extends BaseSectionDeletionHandler {
    private final SectionRepository sectionRepository;

    @Override
    protected void processRequest(SectionDeletionContext context) {
        logger.debug("Section deletion initialization handler processing for section ID: {}", context.getSectionId());
        Section section = sectionRepository.findById(context.getSectionId())
                .orElseThrow(()-> {
                    logger.error("Section with ID {} not found", context.getSectionId());
                    return new SectionNotFoundException(String.format("Section with ID %s not found", context.getSectionId()));
                });
        context.setTargetSection(section);
        logger.debug("Finished section deletion initialization handler processing for section ID: {}", context.getSectionId());
    }
}
