package com.logicerror.e_learning.services.course.operationhandlers;

import com.logicerror.e_learning.entities.teacher.TeacherCourses;
import com.logicerror.e_learning.entities.teacher.TeacherCoursesKey;
import com.logicerror.e_learning.repositories.TeacherCoursesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TeacherCourseAssociationHandler extends BaseCourseOperationHandler {
    private final TeacherCoursesRepository teacherCoursesRepository;


    @Override
    protected void processRequest(CourseCreationContext context) {
        TeacherCourses teacherCourses = TeacherCourses.builder()
                .course(context.getCourse())
                .user(context.getUser())
                .id(TeacherCoursesKey.builder()
                        .courseId(context.getCourse().getId())
                        .userId(context.getUser().getId())
                        .build())
                .build();

        teacherCoursesRepository.save(teacherCourses);
    }
}
