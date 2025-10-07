package com.logicerror.e_learning.courses.services.operationhandlers.update;


import com.logicerror.e_learning.courses.entities.Course;
import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.courses.requests.UpdateCourseRequest;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class CourseUpdateContext {
    private final Long courseId;
    private final UpdateCourseRequest request;
    private final User user;
    private Course existingCourse;
    private Course updatedCourse;
}
