package com.logicerror.e_learning.services.course.operationhandlers.delete;

import com.logicerror.e_learning.services.OperationHandler;
import com.logicerror.e_learning.services.course.operationhandlers.AbstractChainBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CourseDeleteChainBuilder extends AbstractChainBuilder<CourseDeleteContext> {
    private final DeleteCourseHandler courseDeleteHandler;
    private final DeleteValidationHandler validationHandler;
    private final DeleteAuthorizationHandler authorizationHandler;

    @Override
    protected List<OperationHandler<CourseDeleteContext>> getOperationHandlers() {
        return List.of(
                authorizationHandler,
                validationHandler,
                courseDeleteHandler
        );
    }
}
