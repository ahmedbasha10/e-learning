package com.logicerror.e_learning.services.section.operationhandlers.update.fields;

import com.logicerror.e_learning.entities.course.Section;
import com.logicerror.e_learning.requests.course.section.UpdateSectionRequest;
import org.springframework.stereotype.Component;

@Component("sectionDurationFieldUpdater")
public class DurationFieldUpdater implements SectionFieldUpdater{
    @Override
    public void updateField(Section section, UpdateSectionRequest request) {
        if (request.getDuration() != null && request.getDuration() > 0) {
            section.setDuration(request.getDuration());
        }
    }
}
