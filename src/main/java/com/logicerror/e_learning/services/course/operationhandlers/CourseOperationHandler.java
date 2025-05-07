package com.logicerror.e_learning.services.course.operationhandlers;

import com.logicerror.e_learning.services.course.operationhandlers.creation.CourseCreationContext;

public interface CourseOperationHandler {
    void handle(CourseCreationContext courseCreationContext);
    CourseOperationHandler setNextHandler(CourseOperationHandler nextHandler);
}
