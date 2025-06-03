package com.logicerror.e_learning.services.section.operationhandlers.update;

import com.logicerror.e_learning.entities.course.Section;
import com.logicerror.e_learning.repositories.SectionRepository;
import com.logicerror.e_learning.services.section.operationhandlers.update.fields.SectionUpdateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class SectionUpdateHandler extends BaseSectionUpdateHandler{
    private final SectionRepository sectionRepository;
    private final SectionUpdateService sectionUpdateService;

    @Override
    protected void processRequest(SectionUpdateContext sectionCreationContext) {
        log.debug("Updating section with ID: {}", sectionCreationContext.getSectionId());
        sectionUpdateService.update(sectionCreationContext.getUpdatedSection(), sectionCreationContext.getRequest());
        Section updatedSection = sectionRepository.save(sectionCreationContext.getUpdatedSection());
        sectionCreationContext.setUpdatedSection(updatedSection);
        log.debug("Successfully updated section with ID: {}", updatedSection.getId());
    }
}
