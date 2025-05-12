package com.logicerror.e_learning.services.course.operationhandlers.delete;

import com.logicerror.e_learning.services.OperationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class BaseCourseDeleteHandler implements OperationHandler<CourseDeleteContext> {
    protected OperationHandler<CourseDeleteContext> nextHandler;
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void handle(CourseDeleteContext context) {
        processRequest(context);
        if (nextHandler != null) {
            nextHandler.handle(context);
        }
    }

    @Override
    public OperationHandler<CourseDeleteContext> setNextHandler(OperationHandler<CourseDeleteContext> nextHandler) {
        this.nextHandler = nextHandler;
        return nextHandler;
    }

    protected abstract void processRequest(CourseDeleteContext context);
}
