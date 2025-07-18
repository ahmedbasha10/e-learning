package com.logicerror.e_learning.services.section.operationhandlers.update;

import com.logicerror.e_learning.services.AbstractOperationHandlerChainBuilder;
import com.logicerror.e_learning.services.OperationHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class SectionUpdateChainBuilder extends AbstractOperationHandlerChainBuilder<SectionUpdateContext> {
    private final SectionUpdateAuthorizationHandler authorizationHandler;
    private final SectionUpdateHandler sectionUpdateHandler;

    @Override
    protected List<OperationHandler<SectionUpdateContext>> getOperationHandlers() {
        return List.of(
                authorizationHandler,
                sectionUpdateHandler
        );
    }
}
