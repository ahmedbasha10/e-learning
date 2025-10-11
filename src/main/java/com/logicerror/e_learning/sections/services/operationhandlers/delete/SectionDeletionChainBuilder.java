package com.logicerror.e_learning.sections.services.operationhandlers.delete;

import com.logicerror.e_learning.services.AbstractOperationHandlerChainBuilder;
import com.logicerror.e_learning.services.OperationHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SectionDeletionChainBuilder extends AbstractOperationHandlerChainBuilder<SectionDeletionContext> {
    private final SectionDeletionInitializationHandler initializationHandler;
    private final SectionDeletionAuthorizationHandler authorizationHandler;
    private final PreSectionDeletionHandler preSectionDeletionHandler;
    private final SectionDeletionHandler sectionDeletionHandler;
    private final PostSectionDeletionHandler postSectionDeletionHandler;

    @Override
    protected List<OperationHandler<SectionDeletionContext>> getOperationHandlers() {
        return List.of(
            initializationHandler,
            authorizationHandler,
            preSectionDeletionHandler,
            sectionDeletionHandler,
            postSectionDeletionHandler
        );
    }
}
