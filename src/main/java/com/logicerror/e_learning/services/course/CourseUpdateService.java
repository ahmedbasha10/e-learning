package com.logicerror.e_learning.services.course;

import com.logicerror.e_learning.entities.course.Course;
import com.logicerror.e_learning.requests.course.UpdateCourseRequest;
import com.logicerror.e_learning.services.Updater;
import com.logicerror.e_learning.services.course.operationhandlers.update.filedupdaters.CourseFieldUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseUpdateService implements Updater<Course, UpdateCourseRequest> {
    private final List<CourseFieldUpdater> fieldUpdaters;

    @Override
    public void update(Course entity, UpdateCourseRequest updateRequest) {
        fieldUpdaters
                .forEach(fieldUpdater -> fieldUpdater.updateField(entity, updateRequest));
    }
}
