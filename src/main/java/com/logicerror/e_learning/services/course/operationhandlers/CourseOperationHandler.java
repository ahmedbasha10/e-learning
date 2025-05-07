package com.logicerror.e_learning.services.course.operationhandlers;

public interface CourseOperationHandler<T> {
    void handle(T courseCreationContext);
    CourseOperationHandler<T> setNextHandler(CourseOperationHandler<T> nextHandler);
}
