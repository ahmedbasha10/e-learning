package com.logicerror.e_learning.services.section;

import com.logicerror.e_learning.dto.SectionDto;
import com.logicerror.e_learning.entities.course.Section;
import com.logicerror.e_learning.entities.teacher.TeacherCoursesKey;
import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.exceptions.section.SectionNotFoundException;
import com.logicerror.e_learning.mappers.SectionMapper;
import com.logicerror.e_learning.repositories.SectionRepository;
import com.logicerror.e_learning.repositories.TeacherCoursesRepository;
import com.logicerror.e_learning.requests.course.section.CreateSectionRequest;
import com.logicerror.e_learning.requests.course.section.UpdateSectionRequest;
import com.logicerror.e_learning.services.OperationHandler;
import com.logicerror.e_learning.services.section.operationhandlers.create.SectionCreationChainBuilder;
import com.logicerror.e_learning.services.section.operationhandlers.create.SectionCreationContext;
import com.logicerror.e_learning.services.section.operationhandlers.update.SectionUpdateChainBuilder;
import com.logicerror.e_learning.services.section.operationhandlers.update.SectionUpdateContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SectionService implements ISectionService{
    private final SectionRepository sectionRepository;
    private final SectionMapper sectionMapper;
    private final SectionCreationChainBuilder sectionCreationChainBuilder;
    private final SectionUpdateChainBuilder sectionUpdateChainBuilder;
    private final TeacherCoursesRepository teacherCoursesRepository;


    @Override
    public Section getSectionById(Long sectionId) {
        log.debug("Fetching section with ID: {}", sectionId);
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new SectionNotFoundException("Section not found with id: " + sectionId));
        log.info("Section found: {}", section.getTitle());
        return section;
    }

    @Override
    public Section getSectionByTitle(Long courseId, String title) {
        log.debug("Fetching section with title: {}", title);
        Section section = sectionRepository.findByTitleWithCourseId(courseId, title)
                .orElseThrow(() -> new SectionNotFoundException("Section not found with title: " + title));
        log.info("Section found: {}", section.getTitle());
        return section;
    }

    @Override
    @PreAuthorize("hasRole('TEACHER')")
    public Section createSection(CreateSectionRequest createSectionRequest, Long courseId) {
        log.debug("Creating section with title: {} for course ID: {}", createSectionRequest.getTitle(), courseId);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        OperationHandler<SectionCreationContext> operationHandler = sectionCreationChainBuilder.build();
        SectionCreationContext context = new SectionCreationContext(createSectionRequest, courseId, user);
        operationHandler.handle(context);
        log.info("Section created successfully with title: {}", context.getCreatedSection().getTitle());
        return context.getCreatedSection();
    }

    @Override
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public Section updateSection(UpdateSectionRequest updateSectionRequest, Long courseId) {
        log.debug("Creating section with title: {} for course ID: {}", updateSectionRequest.getTitle(), courseId);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        OperationHandler<SectionUpdateContext> operationHandler = sectionUpdateChainBuilder.build();
        SectionUpdateContext context = new SectionUpdateContext(updateSectionRequest, courseId, user);
        operationHandler.handle(context);
        log.info("Section created successfully with title: {}", context.getUpdatedSection().getTitle());
        return context.getUpdatedSection();
    }

    @Override
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public void deleteSection(Long sectionId) {
        log.debug("Deleting section with ID: {}", sectionId);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!user.isTeacher() && !user.isAdmin()){
            throw new AccessDeniedException("User does not have permission to delete sections");
        }

        Section section = getSectionById(sectionId);
        if(user.isTeacher()){
            boolean isOwner = teacherCoursesRepository.existsById(
                    TeacherCoursesKey.builder()
                            .courseId(section.getCourse().getId())
                            .userId(user.getId())
                            .build()
            );
            if(!isOwner) {
                throw new AccessDeniedException("User is not the owner of this section");
            }
        }

        sectionRepository.delete(section);
        log.info("Section with ID: {} deleted successfully", sectionId);
    }

    @Override
    public SectionDto convertToDto(Section section) {
        return sectionMapper.sectionToSectionDto(section);
    }
}
