package com.logicerror.e_learning.services.section.operationhandlers.create;

import com.logicerror.e_learning.exceptions.section.SectionAlreadyExistsException;
import com.logicerror.e_learning.repositories.SectionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class SectionCreationValidationHandler extends BaseSectionCreationHandler{
    private final SectionRepository sectionRepository;

    @Override
    protected void processRequest(SectionCreationContext context) {
        log.debug("Validating section creation request");

        if(sectionRepository.existsByCourseIdAndTitle(context.getCourseId(), context.getRequest().getTitle())) {
            log.error("Section with title {} already exists" , context.getRequest().getTitle());
            throw new SectionAlreadyExistsException(String.format("Section with title %s already exists", context.getRequest().getTitle()));
        }

        if (sectionRepository.existsByCourseIdAndOrder(context.getCourseId(), context.getRequest().getOrder())) {
            log.error("Section with order {} already exists", context.getRequest().getOrder());
            throw new SectionAlreadyExistsException(String.format("Section with order %s already exists", context.getRequest().getOrder()));
        }

        log.debug("Section creation request is valid");
    }
}
