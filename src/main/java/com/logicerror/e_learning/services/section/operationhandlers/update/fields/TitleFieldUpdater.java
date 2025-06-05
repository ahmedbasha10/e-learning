package com.logicerror.e_learning.services.section.operationhandlers.update.fields;

import com.logicerror.e_learning.entities.course.Section;
import com.logicerror.e_learning.exceptions.section.SectionAlreadyExistsException;
import com.logicerror.e_learning.repositories.SectionRepository;
import com.logicerror.e_learning.requests.course.section.UpdateSectionRequest;
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
