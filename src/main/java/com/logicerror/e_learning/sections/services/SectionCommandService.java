package com.logicerror.e_learning.sections.services;

import com.logicerror.e_learning.sections.entities.Section;
import com.logicerror.e_learning.sections.requests.BatchCreateSectionRequest;
import com.logicerror.e_learning.sections.requests.CreateSectionRequest;
import com.logicerror.e_learning.sections.requests.UpdateSectionRequest;

import java.util.List;

public interface SectionCommandService {
    Section createSection(CreateSectionRequest createSectionRequest, Long courseId);
    List<Section> batchCreateSections(BatchCreateSectionRequest batchCreateSectionRequest, Long courseId);
    Section updateSection(UpdateSectionRequest updateSectionRequest, Long sectionId);
    void updateSectionDuration(Long sectionId);
    void deleteSection(Long sectionId);
}
