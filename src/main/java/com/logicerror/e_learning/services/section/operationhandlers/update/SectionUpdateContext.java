package com.logicerror.e_learning.services.section.operationhandlers.update;

import com.logicerror.e_learning.entities.course.Section;
import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.requests.course.section.UpdateSectionRequest;
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
