package com.logicerror.e_learning.services.course.operationhandlers.creation;

import com.logicerror.e_learning.services.OperationHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseCreationChainBuilder {
    private final TeacherCourseAssociationHandler teacherCourseAssociationHandler;
    private final CourseCreationHandler courseCreationHandler;
    private final ValidationHandler validationHandler;
    private final AuthorizationHandler authorizationHandler;

    public OperationHandler<CourseCreationContext> build() {
        authorizationHandler
                .setNextHandler(validationHandler)
                .setNextHandler(courseCreationHandler)
                .setNextHandler(teacherCourseAssociationHandler);

        return authorizationHandler;
    }
}
