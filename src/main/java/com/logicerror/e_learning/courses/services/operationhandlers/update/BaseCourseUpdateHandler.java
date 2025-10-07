package com.logicerror.e_learning.courses.services.operationhandlers.update;

import com.logicerror.e_learning.services.OperationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseCourseUpdateHandler implements OperationHandler<CourseUpdateContext> {

    protected OperationHandler<CourseUpdateContext> nextHandler;
    protected final Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    public void handle(CourseUpdateContext context) {
        processRequest(context);
        if (nextHandler != null) {
            nextHandler.handle(context);
        }
    }

    @Override
    public void setNextHandler(OperationHandler<CourseUpdateContext> nextHandler) {
        this.nextHandler = nextHandler;
    }

    protected abstract void processRequest(CourseUpdateContext context);
}
