package com.logicerror.e_learning.services.section.operationhandlers.delete;

import com.logicerror.e_learning.services.OperationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseSectionDeletionHandler implements OperationHandler<SectionDeletionContext> {
    protected OperationHandler<SectionDeletionContext> nextHandler;
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void handle(SectionDeletionContext context) {
        processRequest(context);
        if (nextHandler != null) {
            nextHandler.handle(context);
        }
    }

    @Override
    public void setNextHandler(OperationHandler<SectionDeletionContext> nextHandler) {
        this.nextHandler = nextHandler;
    }

    protected abstract void processRequest(SectionDeletionContext context);
}
