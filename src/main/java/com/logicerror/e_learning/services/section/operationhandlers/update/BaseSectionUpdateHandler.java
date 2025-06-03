package com.logicerror.e_learning.services.section.operationhandlers.update;

import com.logicerror.e_learning.services.OperationHandler;

public abstract class BaseSectionUpdateHandler implements OperationHandler<SectionUpdateContext> {
    private OperationHandler<SectionUpdateContext> nextHandler;

    @Override
    public void handle(SectionUpdateContext context) {
        // Process the request
        processRequest(context);
        if (nextHandler != null) {
            nextHandler.handle(context);
        }
    }

    @Override
    public OperationHandler<SectionUpdateContext> setNextHandler(OperationHandler<SectionUpdateContext> nextHandler) {
        return this.nextHandler = nextHandler;
    }

    protected abstract void processRequest(SectionUpdateContext sectionCreationContext);
}
