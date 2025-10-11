package com.logicerror.e_learning.sections.services.operationhandlers.update.fields;

import com.logicerror.e_learning.sections.entities.Section;
import com.logicerror.e_learning.sections.exceptions.SectionAlreadyExistsException;
import com.logicerror.e_learning.sections.repositories.SectionRepository;
import com.logicerror.e_learning.sections.requests.UpdateSectionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component("sectionTitleFieldUpdater")
public class TitleFieldUpdater implements SectionFieldUpdater{
    private final SectionRepository sectionRepository;

    @Override
    public void updateField(Section section, UpdateSectionRequest request) {
        if (request.getTitle() != null && !request.getTitle().isEmpty()) {
            if(sectionRepository.existsByCourseIdAndTitle(section.getCourse().getId(), request.getTitle())) {
                throw new SectionAlreadyExistsException("Section with the same title already exists in this course.");
            }
            section.setTitle(request.getTitle());
        }
    }
}
