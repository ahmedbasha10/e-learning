package com.logicerror.e_learning.services.course.operationhandlers.creation;

import com.logicerror.e_learning.services.course.operationhandlers.CourseOperationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseCourseCreationHandler implements CourseOperationHandler<CourseCreationContext> {

    protected CourseOperationHandler<CourseCreationContext> nextHandler;
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Override
    public CourseOperationHandler<CourseCreationContext> setNextHandler(CourseOperationHandler<CourseCreationContext> nextHandler) {
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
