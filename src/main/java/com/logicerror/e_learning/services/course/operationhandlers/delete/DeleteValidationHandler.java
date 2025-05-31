package com.logicerror.e_learning.services.course.operationhandlers.delete;

import com.logicerror.e_learning.entities.course.Course;
import com.logicerror.e_learning.exceptions.course.CourseNotFoundException;
import com.logicerror.e_learning.repositories.CourseRepository;
import com.logicerror.e_learning.repositories.TeacherCoursesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteValidationHandler extends BaseCourseDeleteHandler{
    private final CourseRepository courseRepository;
    private final TeacherCoursesRepository teacherCoursesRepository;

    @Override
    protected void processRequest(CourseDeleteContext context) {
        logger.debug("Validating course deletion request");
        Course course = courseRepository.findById(context.getCourseId())
                .orElseThrow(() -> new CourseNotFoundException("Course not found with ID: " + context.getCourseId()));
        context.setDeletedCourse(course);
        if(context.getUser().isAdmin()){
            teacherCoursesRepository.findByCourseId(context.getCourseId())
                    .stream()
                    .map(teacherCourse -> teacherCourse.getUser().getId())
                    .forEach(context::addTeacherId);
        }
        logger.debug("Course validation successful");
    }
}
