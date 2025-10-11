package com.logicerror.e_learning.sections.services.operationhandlers.update;

import com.logicerror.e_learning.sections.entities.Section;
import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.sections.requests.UpdateSectionRequest;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class SectionUpdateContext {
    private final UpdateSectionRequest request;
    private final Long sectionId;
    private final User user;
    private Section updatedSection;
}
