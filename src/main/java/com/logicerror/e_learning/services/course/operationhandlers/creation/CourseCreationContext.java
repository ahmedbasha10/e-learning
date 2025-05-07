package com.logicerror.e_learning.services.course.operationhandlers.creation;

import com.logicerror.e_learning.entities.course.Course;
import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.requests.course.CreateCourseRequest;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class CourseCreationContext {
    private final CreateCourseRequest request;
    private final User user;
    private Course course;
}
