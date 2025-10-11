package com.logicerror.e_learning.sections.services.operationhandlers.update.fields;

import com.logicerror.e_learning.sections.entities.Section;
import com.logicerror.e_learning.sections.requests.UpdateSectionRequest;
import com.logicerror.e_learning.services.Updater;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class SectionUpdateService implements Updater<Section, UpdateSectionRequest> {

    private final List<SectionFieldUpdater> fieldUpdaters;

    @Override
    public void update(Section entity, UpdateSectionRequest updateRequest) {
        fieldUpdaters
                .forEach(fieldUpdater -> fieldUpdater.updateField(entity, updateRequest));
    }
}
