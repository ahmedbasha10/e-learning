package com.logicerror.e_learning.sections.services.operationhandlers.create;

import com.logicerror.e_learning.sections.entities.Section;
import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.sections.requests.CreateSectionRequest;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class SectionCreationContext {
    private final CreateSectionRequest request;
    private final Long courseId;
    private final User user;
    private Section createdSection;
}
