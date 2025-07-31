package com.logicerror.e_learning.services.video.operationhandlers.update;

import com.logicerror.e_learning.services.OperationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseVideoUpdateHandler implements OperationHandler<VideoUpdateContext> {
    protected OperationHandler<VideoUpdateContext> nextHandler;
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void handle(VideoUpdateContext context) {
        processRequest(context);
        if (nextHandler != null) {
            nextHandler.handle(context);
        }
    }

    @Override
    public void setNextHandler(OperationHandler<VideoUpdateContext> nextHandler) {
        this.nextHandler = nextHandler;
    }

    protected abstract void processRequest(VideoUpdateContext context);
}
