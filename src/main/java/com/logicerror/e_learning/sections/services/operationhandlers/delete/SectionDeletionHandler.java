package com.logicerror.e_learning.sections.services.operationhandlers.delete;

import com.logicerror.e_learning.sections.repositories.SectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SectionDeletionHandler extends BaseSectionDeletionHandler {
    private final SectionRepository sectionRepository;

    @Override
    protected void processRequest(SectionDeletionContext context) {
        logger.info("Section deletion handler started for section: {}", context.getSectionId());
        // Delete the section from the repository
        sectionRepository.deleteById(context.getSectionId());
        logger.info("Section deletion handler completed for section: {}", context.getSectionId());
    }
}
