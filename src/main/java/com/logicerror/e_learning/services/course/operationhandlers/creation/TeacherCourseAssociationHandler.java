package com.logicerror.e_learning.services.course.operationhandlers.creation;

import com.logicerror.e_learning.entities.teacher.TeacherCourses;
import com.logicerror.e_learning.entities.teacher.TeacherCoursesKey;
import com.logicerror.e_learning.exceptions.general.ResourceNotFoundException;
import com.logicerror.e_learning.repositories.TeacherCoursesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TeacherCourseAssociationHandler extends BaseCourseCreationHandler {
    private final TeacherCoursesRepository teacherCoursesRepository;

    @Override
    protected void processRequest(CourseCreationContext context) {
        logger.info("Associating teacher with course: {}", context.getCourse().getTitle());
        if (context.getUser() == null || context.getCourse() == null) {
            logger.error("Cannot associate teacher with course: User or Course is null");
            throw new ResourceNotFoundException("Cannot associate teacher with course: User or Course is null");
        }

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
