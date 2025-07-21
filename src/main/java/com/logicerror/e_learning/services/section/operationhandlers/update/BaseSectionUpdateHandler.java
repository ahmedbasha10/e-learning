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
    public void setNextHandler(OperationHandler<SectionUpdateContext> nextHandler) {
        this.nextHandler = nextHandler;
    }

    protected abstract void processRequest(SectionUpdateContext sectionCreationContext);
}
