package com.logicerror.e_learning.sections.services.operationhandlers.update.fields;

import com.logicerror.e_learning.sections.entities.Section;
import com.logicerror.e_learning.sections.exceptions.SectionAlreadyExistsException;
import com.logicerror.e_learning.sections.repositories.SectionRepository;
import com.logicerror.e_learning.sections.requests.UpdateSectionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OrderFieldUpdater implements SectionFieldUpdater {
    private final SectionRepository sectionRepository;

    @Override
    public void updateField(Section section, UpdateSectionRequest request) {
        if (request.getOrder() != null && request.getOrder() >= 0) {
            if(sectionRepository.existsByCourseIdAndOrder(section.getCourse().getId(), request.getOrder())){
                throw new SectionAlreadyExistsException("Section with the same order already exists in this course.");
            }
            section.setOrder(request.getOrder());
        }
    }
}
