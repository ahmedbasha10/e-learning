package com.logicerror.e_learning.videos.services.operationhandlers.create;

import com.logicerror.e_learning.services.OperationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseVideoCreationHandler implements OperationHandler<VideoCreationContext> {
    protected OperationHandler<VideoCreationContext> nextHandler;
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void handle(VideoCreationContext context) {
        processRequest(context);
        if (nextHandler != null) {
            nextHandler.handle(context);
        }
    }

    @Override
    public void setNextHandler(OperationHandler<VideoCreationContext> nextHandler) {
        this.nextHandler = nextHandler;
    }

    protected abstract void processRequest(VideoCreationContext context);
}
