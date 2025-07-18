package com.logicerror.e_learning.services.course.operationhandlers.update;


import com.logicerror.e_learning.services.OperationHandler;
import com.logicerror.e_learning.services.AbstractOperationHandlerChainBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CourseUpdateChainBuilder extends AbstractOperationHandlerChainBuilder<CourseUpdateContext> {
    private final UpdateValidationHandler validationHandler;;
    private final UpdateAuthorizationHandler authorizationHandler;
    private final CourseUpdateHandler courseUpdateHandler;

    @Override
    protected List<OperationHandler<CourseUpdateContext>> getOperationHandlers() {
        return List.of(
                authorizationHandler,
                validationHandler,
                courseUpdateHandler
        );
    }
}
