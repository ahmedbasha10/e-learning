package com.logicerror.e_learning.services.section.operationhandlers.create;

import com.logicerror.e_learning.entities.teacher.TeacherCoursesKey;
import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.repositories.TeacherCoursesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SectionCreationAuthorizationHandler extends BaseSectionCreationHandler {

    private final TeacherCoursesRepository teacherCoursesRepository;

    @Override
    protected void processRequest(SectionCreationContext context) {
        User user = context.getUser();

        if (!user.isTeacher()) {
            log.error("User does not have permission to create a section");
            throw new AccessDeniedException("User does not have permission to create a section");
        }


        boolean isOwner = teacherCoursesRepository.existsById(
                TeacherCoursesKey.builder()
                        .courseId(context.getCourseId())
                        .userId(user.getId())
                        .build()
        );

        if (!isOwner) {
            log.error("User is not the owner of the course");
            throw new AccessDeniedException("User is not the owner of the course");
        }


        log.debug("User has permission to create a section");
    }
}
