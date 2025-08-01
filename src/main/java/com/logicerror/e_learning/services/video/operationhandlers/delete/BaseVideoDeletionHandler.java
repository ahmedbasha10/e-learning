package com.logicerror.e_learning.services.video.operationhandlers.delete;

import com.logicerror.e_learning.services.OperationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseVideoDeletionHandler implements OperationHandler<VideoDeletionContext> {
    protected OperationHandler<VideoDeletionContext> nextHandler;
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void handle(VideoDeletionContext context) {
        processRequest(context);
        if (nextHandler != null) {
            nextHandler.handle(context);
        }
    }

    @Override
    public void setNextHandler(OperationHandler<VideoDeletionContext> nextHandler) {
        this.nextHandler = nextHandler;
    }

    protected abstract void processRequest(VideoDeletionContext context);
}
