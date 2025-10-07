package com.logicerror.e_learning.courses.services.operationhandlers.creation;

import com.logicerror.e_learning.services.OperationHandler;
import com.logicerror.e_learning.services.AbstractOperationHandlerChainBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CourseCreationChainBuilder extends AbstractOperationHandlerChainBuilder<CourseCreationContext> {
    private final TeacherCourseAssociationHandler teacherCourseAssociationHandler;
    private final CourseCreationHandler courseCreationHandler;
    private final ValidationHandler validationHandler;
    private final AuthorizationHandler authorizationHandler;

    @Override
    protected List<OperationHandler<CourseCreationContext>> getOperationHandlers() {
        return List.of(
                authorizationHandler,
                validationHandler,
                courseCreationHandler,
                teacherCourseAssociationHandler
        );
    }
}
