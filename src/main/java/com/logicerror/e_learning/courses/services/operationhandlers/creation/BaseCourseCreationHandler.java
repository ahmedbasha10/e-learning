package com.logicerror.e_learning.courses.services.operationhandlers.creation;

import com.logicerror.e_learning.services.OperationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseCourseCreationHandler implements OperationHandler<CourseCreationContext> {

    protected OperationHandler<CourseCreationContext> nextHandler;
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Override
    public void setNextHandler(OperationHandler<CourseCreationContext> nextHandler) {
        this.nextHandler = nextHandler;
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
