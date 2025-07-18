package com.logicerror.e_learning.services.course.operationhandlers.update.filedupdaters;

import com.logicerror.e_learning.entities.course.Course;
import com.logicerror.e_learning.requests.course.UpdateCourseRequest;

public interface CourseFieldUpdater {
    void updateField(Course course, UpdateCourseRequest request);
}
