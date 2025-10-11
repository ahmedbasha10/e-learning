package com.logicerror.e_learning.sections.services.operationhandlers.update;

import com.logicerror.e_learning.sections.entities.Section;
import com.logicerror.e_learning.sections.repositories.SectionRepository;
import com.logicerror.e_learning.sections.services.operationhandlers.update.fields.SectionUpdateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
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
