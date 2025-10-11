package com.logicerror.e_learning.sections.services.operationhandlers.create;

import com.logicerror.e_learning.services.AbstractOperationHandlerChainBuilder;
import com.logicerror.e_learning.services.OperationHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class SectionCreationChainBuilder extends AbstractOperationHandlerChainBuilder<SectionCreationContext> {
    private final SectionCreationAuthorizationHandler authorizationHandler;
    private final SectionCreationValidationHandler validationHandler;
    private final SectionCreationHandler sectionCreationHandler;

    @Override
    protected List<OperationHandler<SectionCreationContext>> getOperationHandlers() {
        return List.of(
                authorizationHandler,
                validationHandler,
                sectionCreationHandler
        );
    }
}
