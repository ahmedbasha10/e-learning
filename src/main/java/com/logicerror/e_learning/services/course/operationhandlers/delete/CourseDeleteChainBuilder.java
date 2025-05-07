package com.logicerror.e_learning.services.course.operationhandlers.delete;

import com.logicerror.e_learning.services.course.operationhandlers.CourseOperationHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseDeleteChainBuilder {
    private final DeleteCourseHandler courseDeleteHandler;
    private final DeleteValidationHandler validationHandler;
    private final DeleteAuthorizationHandler authorizationHandler;

    public CourseOperationHandler<CourseDeleteContext> build() {
        authorizationHandler
                .setNextHandler(validationHandler)
                .setNextHandler(courseDeleteHandler);

        return authorizationHandler;
    }
}
