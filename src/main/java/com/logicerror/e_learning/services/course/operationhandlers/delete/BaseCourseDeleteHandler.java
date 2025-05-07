package com.logicerror.e_learning.services.course.operationhandlers.delete;

import com.logicerror.e_learning.services.course.operationhandlers.CourseOperationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class BaseCourseDeleteHandler implements CourseOperationHandler<CourseDeleteContext> {
    protected CourseOperationHandler<CourseDeleteContext> nextHandler;
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void handle(CourseDeleteContext context) {
        processRequest(context);
        if (nextHandler != null) {
            nextHandler.handle(context);
        }
    }

    @Override
    public CourseOperationHandler<CourseDeleteContext> setNextHandler(CourseOperationHandler<CourseDeleteContext> nextHandler) {
        this.nextHandler = nextHandler;
        return nextHandler;
    }

    protected abstract void processRequest(CourseDeleteContext context);
}
