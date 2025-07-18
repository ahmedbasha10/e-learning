package com.logicerror.e_learning.services.course.operationhandlers.update.filedupdaters;

import com.logicerror.e_learning.entities.course.Course;
import com.logicerror.e_learning.exceptions.course.CourseTitleAlreadyExistsException;
import com.logicerror.e_learning.repositories.CourseRepository;
import com.logicerror.e_learning.requests.course.UpdateCourseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TitleFieldUpdater implements CourseFieldUpdater{
    private final CourseRepository courseRepository;

    @Override
    public void updateField(Course course, UpdateCourseRequest request) {
        if (request.getTitle() != null) {
            if(courseRepository.existsByTitle(request.getTitle())) {
                throw new CourseTitleAlreadyExistsException("Course already exists with title: " + request.getTitle());
            }
            course.setTitle(request.getTitle());
        }
    }
}
