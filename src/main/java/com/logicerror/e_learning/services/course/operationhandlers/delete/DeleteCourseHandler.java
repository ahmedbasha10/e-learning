package com.logicerror.e_learning.services.course.operationhandlers.delete;

import com.logicerror.e_learning.entities.teacher.TeacherCoursesKey;
import com.logicerror.e_learning.repositories.CourseRepository;
import com.logicerror.e_learning.repositories.TeacherCoursesRepository;
import com.logicerror.e_learning.repositories.UserEnrollmentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteCourseHandler extends BaseCourseDeleteHandler{
    private final CourseRepository courseRepository;
    private final TeacherCoursesRepository teacherCoursesRepository;
    private final UserEnrollmentsRepository userEnrollmentsRepository;


    @Override
    protected void processRequest(CourseDeleteContext context) {
        logger.debug("Deleting course with ID: {}", context.getCourseId());
        for(Long teacherId : context.getTeachersId()) {
            logger.debug("Deleting teacher-course association for teacher ID: {}", teacherId);
            teacherCoursesRepository.deleteById(
                    TeacherCoursesKey.builder()
                            .courseId(context.getCourseId())
                            .userId(teacherId)
                            .build()
            );
        }
        userEnrollmentsRepository.deleteByCourseId(context.getCourseId());
        courseRepository.deleteById(context.getCourseId());
        logger.debug("Course with ID: {} deleted successfully", context.getCourseId());
    }
}
