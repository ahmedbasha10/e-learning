package com.logicerror.e_learning.services.course.operationhandlers.update;


import com.logicerror.e_learning.entities.course.Course;
import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.requests.course.UpdateCourseRequest;
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
