package com.logicerror.e_learning.services.course.operationhandlers.update.filedupdaters;

import com.logicerror.e_learning.entities.course.Course;
import com.logicerror.e_learning.requests.course.UpdateCourseRequest;
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
