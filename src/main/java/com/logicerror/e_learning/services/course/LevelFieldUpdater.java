package com.logicerror.e_learning.services.course;

import com.logicerror.e_learning.entities.course.Course;
import com.logicerror.e_learning.requests.course.UpdateCourseRequest;
import org.springframework.stereotype.Component;

@Component
public class LevelFieldUpdater implements CourseFieldUpdater{
    @Override
    public void updateField(Course course, UpdateCourseRequest request) {
        if (request.getLevel() != null) {
            course.setLevel(request.getLevel());
        }
    }
}
