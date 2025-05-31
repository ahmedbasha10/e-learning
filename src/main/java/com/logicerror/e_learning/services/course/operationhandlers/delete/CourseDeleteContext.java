package com.logicerror.e_learning.services.course.operationhandlers.delete;

import com.logicerror.e_learning.entities.course.Course;
import com.logicerror.e_learning.entities.user.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Data
public class CourseDeleteContext {
    private final Long courseId;
    private List<Long> teachersId;
    private final User user;
    private Course deletedCourse;

    public void addTeacherId(Long teacherId) {
        if(teachersId == null) {
           teachersId = new ArrayList<>();
        }
        teachersId.add(teacherId);
    }
}
