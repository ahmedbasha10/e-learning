package com.logicerror.e_learning.services.course.operationhandlers.update;

import com.logicerror.e_learning.entities.teacher.TeacherCoursesKey;
import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.repositories.TeacherCoursesRepository;
import com.logicerror.e_learning.services.authorization.CourseAuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateAuthorizationHandler extends BaseCourseUpdateHandler{
    private final CourseAuthorizationService courseAuthorizationService;

    @Override
    protected void processRequest(CourseUpdateContext context) {
        logger.debug("Checking authorization for course update");
        User user = context.getUser();
        if (!user.isTeacher() && !user.isAdmin()) {
            logger.error("User does not have permission to update a course");
            throw new AccessDeniedException("User does not have permission to update a course");
        }

        if(user.isTeacher()) {
            boolean isOwner = courseAuthorizationService.isCourseOwner(context.getCourseId(), user.getId());

            if (!isOwner) {
                logger.error("User is not the owner of the course");
                throw new AccessDeniedException("User is not the owner of the course");
            }
        }

        logger.debug("User has permission to update the course");
    }
}
