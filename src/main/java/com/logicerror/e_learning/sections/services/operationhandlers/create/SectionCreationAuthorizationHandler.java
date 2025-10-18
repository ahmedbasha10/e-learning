package com.logicerror.e_learning.sections.services.operationhandlers.create;

import com.logicerror.e_learning.courses.security.CourseAuthorizationService;
import com.logicerror.e_learning.entities.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SectionCreationAuthorizationHandler extends BaseSectionCreationHandler {

    private final CourseAuthorizationService courseAuthorizationService;

    @Override
    protected void processRequest(SectionCreationContext context) {
        User user = context.getUser();

        if(!courseAuthorizationService.hasAccessToModifyCourse(context.getCourseId(), user)) {
            log.error("User with ID: {} does not have permission to create section in course ID: {}", user.getId(), context.getCourseId());
            throw new AccessDeniedException("User does not have permission to create section in this course");
        }

        log.debug("User has permission to create a section");
    }
}
