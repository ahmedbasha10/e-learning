package com.logicerror.e_learning.services.course.operationhandlers.creation;

import com.logicerror.e_learning.services.OperationHandler;
import com.logicerror.e_learning.services.course.operationhandlers.AbstractChainBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CourseCreationChainBuilder extends AbstractChainBuilder<CourseCreationContext> {
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
