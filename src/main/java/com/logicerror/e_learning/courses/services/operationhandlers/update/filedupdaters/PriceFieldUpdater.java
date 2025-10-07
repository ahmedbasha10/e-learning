package com.logicerror.e_learning.courses.services.operationhandlers.update.filedupdaters;

import com.logicerror.e_learning.courses.entities.Course;
import com.logicerror.e_learning.courses.requests.UpdateCourseRequest;
import org.springframework.stereotype.Component;

@Component
public class PriceFieldUpdater implements CourseFieldUpdater{
    @Override
    public void updateField(Course course, UpdateCourseRequest request) {
        if (request.getPrice() != null) {
            course.setPrice(request.getPrice());
        }
    }
}
