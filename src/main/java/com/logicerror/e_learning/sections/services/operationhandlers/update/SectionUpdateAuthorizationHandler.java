package com.logicerror.e_learning.sections.services.operationhandlers.update;

import com.logicerror.e_learning.sections.entities.Section;
import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.sections.exceptions.SectionNotFoundException;
import com.logicerror.e_learning.sections.repositories.SectionRepository;
import com.logicerror.e_learning.courses.security.CourseAuthorizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class SectionUpdateAuthorizationHandler extends BaseSectionUpdateHandler{
    private final SectionRepository sectionRepository;
    private final CourseAuthorizationService courseAuthorizationService;

    @Override
    protected void processRequest(SectionUpdateContext context) {
        log.debug("Processing section update authorization for section ID: {}", context.getSectionId());
        User user = context.getUser();

        Optional<Section> section = sectionRepository.findById(context.getSectionId());
        if (section.isEmpty()) {
            throw new SectionNotFoundException("Section is not found with id: " + context.getSectionId());
        }

        if(!courseAuthorizationService.hasAccessToModifyCourse(section.get().getCourse().getId(), user)) {
            log.error("User {} is not authorized to update section with ID: {}", user.getUsername(), context.getSectionId());
            throw new AccessDeniedException("User is not authorized to update section with id: " + context.getSectionId());
        }

        context.setUpdatedSection(section.get());
        log.debug("User has permission to update the section with ID: {}", context.getSectionId());
    }
}
