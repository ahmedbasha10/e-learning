package com.logicerror.e_learning.services.section.operationhandlers.create;

import com.logicerror.e_learning.exceptions.section.SectionAlreadyExistsException;
import com.logicerror.e_learning.repositories.SectionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class SectionCreationValidationHandler extends BaseSectionCreationHandler{
    private final SectionRepository sectionRepository;

    @Override
    protected void processRequest(SectionCreationContext context) {
        log.debug("Validating section creation request");

        Map<String, Boolean> result = sectionRepository.checkDuplicates(
                context.getCourse().getId(),
                context.getRequest().getTitle(),
                context.getRequest().getOrder()
        );

        if(Boolean.TRUE.equals(result.get("titleExists"))) {
            log.error("Section with the same title already exists");
            throw new SectionAlreadyExistsException("Section with the same title already exists");
        }

        if (Boolean.TRUE.equals(result.get("orderExists"))) {
            log.error("Section with the same order already exists");
            throw new SectionAlreadyExistsException("Section with the same order already exists");
        }

        log.debug("Section creation request is valid");
    }
}
