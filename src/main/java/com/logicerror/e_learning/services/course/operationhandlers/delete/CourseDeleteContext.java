package com.logicerror.e_learning.services.course.operationhandlers.delete;

import com.logicerror.e_learning.entities.course.Course;
import com.logicerror.e_learning.entities.user.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class CourseDeleteContext {
    private final Long courseId;
    private final User user;
    private Course deletedCourse;

}
