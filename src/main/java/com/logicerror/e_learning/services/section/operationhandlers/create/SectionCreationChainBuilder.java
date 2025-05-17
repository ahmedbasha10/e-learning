package com.logicerror.e_learning.services.section.operationhandlers.create;

import com.logicerror.e_learning.services.OperationHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SectionCreationChainBuilder {
    private final SectionCreationAuthorizationHandler authorizationHandler;
    private final SectionCreationValidationHandler validationHandler;
    private final SectionCreationHandler sectionCreationHandler;

    public OperationHandler<SectionCreationContext> build() {
        authorizationHandler
                .setNextHandler(validationHandler)
                .setNextHandler(sectionCreationHandler);

        return authorizationHandler;
    }
}
