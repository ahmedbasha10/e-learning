package com.logicerror.e_learning.services.section.operationhandlers.create;

import com.logicerror.e_learning.entities.course.Section;
import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.requests.course.section.CreateSectionRequest;
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
