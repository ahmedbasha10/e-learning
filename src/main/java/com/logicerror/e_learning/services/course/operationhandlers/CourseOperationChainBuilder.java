package com.logicerror.e_learning.services.course.operationhandlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseOperationChainBuilder<T> {
    private final TeacherCourseAssociationHandler teacherCourseAssociationHandler;
    private final CourseCreationHandler courseCreationHandler;
    private final ValidationHandler validationHandler;
    private final AuthorizationHandler authorizationHandler;

    public CourseOperationHandler build() {
        authorizationHandler
                .setNextHandler(validationHandler)
                .setNextHandler(courseCreationHandler)
                .setNextHandler(teacherCourseAssociationHandler);

        return authorizationHandler;
    }
}
