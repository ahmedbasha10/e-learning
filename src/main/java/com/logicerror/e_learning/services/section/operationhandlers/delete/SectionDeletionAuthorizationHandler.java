package com.logicerror.e_learning.services.section.operationhandlers.delete;

import com.logicerror.e_learning.services.authorization.CourseAuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SectionDeletionAuthorizationHandler extends BaseSectionDeletionHandler{
    private final CourseAuthorizationService courseAuthorizationService;

    @Override
    protected void processRequest(SectionDeletionContext context) {
        logger.debug("Processing section deletion authorization for user {} and section id {}",
                context.getUser().getUsername(), context.getSectionId());

        boolean isAuthorized = courseAuthorizationService.hasAccessToModifyCourse(
                context.getTargetSection().getCourse().getId(), context.getUser());

        if (!isAuthorized) {
            logger.error("User {} is not authorized to delete section with id: {}", context.getUser().getUsername(), context.getSectionId());
            throw new AccessDeniedException(String.format("User is not authorized to delete section with id: %s", context.getSectionId()));
        }

        logger.debug("User {} has permission to delete a section with id: {}", context.getUser().getUsername(), context.getSectionId());
    }
}
