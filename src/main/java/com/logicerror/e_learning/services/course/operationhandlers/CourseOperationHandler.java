package com.logicerror.e_learning.services.course.operationhandlers;

public interface CourseOperationHandler {
    void handle(CourseCreationContext courseCreationContext);
    CourseOperationHandler setNextHandler(CourseOperationHandler nextHandler);
}
