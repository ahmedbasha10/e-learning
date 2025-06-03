package com.logicerror.e_learning.services.section.operationhandlers.update.fields;

import com.logicerror.e_learning.entities.course.Section;
import com.logicerror.e_learning.requests.course.section.UpdateSectionRequest;

public interface SectionFieldUpdater {
    void updateField(Section section, UpdateSectionRequest request);
}
