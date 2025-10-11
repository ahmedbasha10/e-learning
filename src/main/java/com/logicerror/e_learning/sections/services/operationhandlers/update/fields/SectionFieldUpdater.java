package com.logicerror.e_learning.sections.services.operationhandlers.update.fields;

import com.logicerror.e_learning.sections.entities.Section;
import com.logicerror.e_learning.sections.requests.UpdateSectionRequest;

public interface SectionFieldUpdater {
    void updateField(Section section, UpdateSectionRequest request);
}
