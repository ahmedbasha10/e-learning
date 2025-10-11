package com.logicerror.e_learning.courses.services.operationhandlers.update.filedupdaters;

import com.logicerror.e_learning.courses.entities.Course;
import com.logicerror.e_learning.courses.exceptions.CourseTitleAlreadyExistsException;
import com.logicerror.e_learning.courses.repositories.CourseRepository;
import com.logicerror.e_learning.courses.requests.UpdateCourseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TitleFieldUpdater implements CourseFieldUpdater{
    private final CourseRepository courseRepository;

    @Override
    public void updateField(Course course, UpdateCourseRequest request) {
        if (request.getTitle() != null && !request.getTitle().equals(course.getTitle())) {
            if(courseRepository.existsByTitle(request.getTitle())) {
                throw new CourseTitleAlreadyExistsException("Course already exists with title: " + request.getTitle());
            }
            course.setTitle(request.getTitle());
        }
    }
}
