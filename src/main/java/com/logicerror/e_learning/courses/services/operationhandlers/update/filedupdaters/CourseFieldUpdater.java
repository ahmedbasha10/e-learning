package com.logicerror.e_learning.courses.services.operationhandlers.update.filedupdaters;

import com.logicerror.e_learning.courses.entities.Course;
import com.logicerror.e_learning.courses.requests.UpdateCourseRequest;

public interface CourseFieldUpdater {
    void updateField(Course course, UpdateCourseRequest request);
}
