package com.logicerror.e_learning.services.course;

import com.logicerror.e_learning.entities.course.Course;
import com.logicerror.e_learning.requests.course.UpdateCourseRequest;
import org.springframework.stereotype.Component;

@Component
public class DurationFieldUpdater implements CourseFieldUpdater{
    @Override
    public void updateField(Course course, UpdateCourseRequest request) {
        if (request.getDuration() != null && request.getDuration() != 0) {
            course.setDuration(request.getDuration());
        }
    }
}
