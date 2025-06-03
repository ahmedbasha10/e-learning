package com.logicerror.e_learning.services.section.operationhandlers.update.fields;

import com.logicerror.e_learning.entities.course.Section;
import com.logicerror.e_learning.exceptions.section.SectionAlreadyExistsException;
import com.logicerror.e_learning.repositories.SectionRepository;
import com.logicerror.e_learning.requests.course.section.UpdateSectionRequest;
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
