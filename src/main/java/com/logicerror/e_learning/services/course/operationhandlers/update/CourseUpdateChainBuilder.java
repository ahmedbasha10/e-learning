package com.logicerror.e_learning.services.course.operationhandlers.update;


import com.logicerror.e_learning.services.course.operationhandlers.CourseOperationHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseUpdateChainBuilder {
    private final UpdateValidationHandler validationHandler;;
    private final UpdateAuthorizationHandler authorizationHandler;
    private final CourseUpdateHandler courseUpdateHandler;

    public CourseOperationHandler<CourseUpdateContext> build() {
        authorizationHandler
                .setNextHandler(validationHandler)
                .setNextHandler(courseUpdateHandler);

        return authorizationHandler;
    }
}
