package com.logicerror.e_learning.services.section.operationhandlers.update;

import com.logicerror.e_learning.entities.course.Section;
import com.logicerror.e_learning.entities.teacher.TeacherCoursesKey;
import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.exceptions.section.SectionNotFoundException;
import com.logicerror.e_learning.repositories.SectionRepository;
import com.logicerror.e_learning.repositories.TeacherCoursesRepository;
import com.logicerror.e_learning.services.authorization.CourseAuthorizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class SectionUpdateAuthorizationHandler extends BaseSectionUpdateHandler{
    private final SectionRepository sectionRepository;
    private final CourseAuthorizationService courseAuthorizationService;

    @Override
    protected void processRequest(SectionUpdateContext context) {
        log.debug("Processing section update authorization for section ID: {}", context.getSectionId());
        User user = context.getUser();
        if (!user.isTeacher() && !user.isAdmin()) {
            throw new AccessDeniedException("User does not have permission to update a section");
        }

        Optional<Section> section = sectionRepository.findById(context.getSectionId());
        if (section.isEmpty()) {
            throw new SectionNotFoundException("Section is not found with id: " + context.getSectionId());
        }

        if (user.isTeacher()) {
            boolean isOwner = courseAuthorizationService.isCourseOwner(section.get().getCourse().getId(), user.getId());

            if (!isOwner) {
                throw new AccessDeniedException("User is not the owner of the course");
            }
        }

        context.setUpdatedSection(section.get());
        log.debug("User has permission to update the section with ID: {}", context.getSectionId());
    }
}
