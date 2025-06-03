package com.logicerror.e_learning.services.section.operationhandlers.update;

import com.logicerror.e_learning.services.OperationHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SectionUpdateChainBuilder {
    private final SectionUpdateAuthorizationHandler authorizationHandler;
    private final SectionUpdateHandler sectionUpdateHandler;

    public OperationHandler<SectionUpdateContext> build() {
        authorizationHandler
                .setNextHandler(sectionUpdateHandler);

        return authorizationHandler;
    }
}
