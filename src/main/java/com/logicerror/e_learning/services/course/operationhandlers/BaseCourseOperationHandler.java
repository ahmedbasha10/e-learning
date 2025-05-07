package com.logicerror.e_learning.services.course.operationhandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseCourseOperationHandler implements CourseOperationHandler {

    protected CourseOperationHandler nextHandler;
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Override
    public CourseOperationHandler setNextHandler(CourseOperationHandler nextHandler) {
        this.nextHandler = nextHandler;
        return nextHandler;
    }

    @Override
    public void handle(CourseCreationContext context) {
        processRequest(context);
        if (nextHandler != null) {
            nextHandler.handle(context);
        }
    }

    protected abstract void processRequest(CourseCreationContext context);
}
