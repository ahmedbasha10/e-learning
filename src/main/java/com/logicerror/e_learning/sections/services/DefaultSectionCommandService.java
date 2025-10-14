package com.logicerror.e_learning.sections.services;

import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.sections.entities.Section;
import com.logicerror.e_learning.sections.repositories.SectionRepository;
import com.logicerror.e_learning.sections.requests.BatchCreateSectionRequest;
import com.logicerror.e_learning.sections.requests.CreateSectionRequest;
import com.logicerror.e_learning.sections.requests.UpdateSectionRequest;
import com.logicerror.e_learning.sections.services.operationhandlers.create.SectionCreationChainBuilder;
import com.logicerror.e_learning.sections.services.operationhandlers.create.SectionCreationContext;
import com.logicerror.e_learning.sections.services.operationhandlers.delete.SectionDeletionChainBuilder;
import com.logicerror.e_learning.sections.services.operationhandlers.delete.SectionDeletionContext;
import com.logicerror.e_learning.sections.services.operationhandlers.update.SectionUpdateChainBuilder;
import com.logicerror.e_learning.sections.services.operationhandlers.update.SectionUpdateContext;
import com.logicerror.e_learning.services.OperationHandler;
import com.logicerror.e_learning.services.user.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultSectionCommandService implements SectionCommandService{
    private final IUserService userService;
    private final SectionRepository sectionRepository;
    private final SectionCreationChainBuilder sectionCreationChainBuilder;
    private final SectionUpdateChainBuilder sectionUpdateChainBuilder;
    private final SectionDeletionChainBuilder sectionDeletionChainBuilder;

    @Override
    @PreAuthorize("hasRole('TEACHER')")
    @Transactional
    public Section createSection(CreateSectionRequest createSectionRequest, Long courseId) {
        log.debug("Creating section with title: {} for course ID: {}", createSectionRequest.getTitle(), courseId);
        User user = userService.getAuthenticatedUser();
        Section section = createSectionProcess(createSectionRequest, courseId, user);
        log.info("Section created successfully with title: {}", section.getTitle());
        return section;
    }

    @Override
    @PreAuthorize("hasRole('TEACHER')")
    @Transactional
    public List<Section> batchCreateSections(BatchCreateSectionRequest batchCreateSectionRequest, Long courseId) {
        log.debug("Batch creating sections for course ID: {}", courseId);
        Assert.notNull(batchCreateSectionRequest, "Batch create section request must not be null");
        Assert.notNull(courseId, "Course ID must not be null");
        User user = userService.getAuthenticatedUser();
        List<Section> sections = new ArrayList<>();
        for(CreateSectionRequest createSectionRequest : batchCreateSectionRequest.getCreateSectionRequests()) {
            Assert.notNull(createSectionRequest, "Create section request must not be null");
            Section section = createSectionProcess(createSectionRequest, courseId, user);
            sections.add(section);
        }
        return sections;
    }

    @Override
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    @Transactional
    public Section updateSection(UpdateSectionRequest updateSectionRequest, Long sectionId) {
        log.debug("Creating section with title: {} for course ID: {}", updateSectionRequest.getTitle(), sectionId);
        User user = userService.getAuthenticatedUser();
        OperationHandler<SectionUpdateContext> operationHandler = sectionUpdateChainBuilder.build();
        SectionUpdateContext context = new SectionUpdateContext(updateSectionRequest, sectionId, user);
        operationHandler.handle(context);
        log.info("Section created successfully with title: {}", context.getUpdatedSection().getTitle());
        return context.getUpdatedSection();
    }

    @Override
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    @Transactional
    public void updateSectionDuration(Long sectionId) {
        Assert.notNull(sectionId, "Section must not be null");
        log.debug("Updating duration for section with ID: {}", sectionId);
        Integer duration = sectionRepository.calculateSectionDuration(sectionId);
        sectionRepository.updateSectionDuration(sectionId, duration);
        log.info("Updated duration for section with ID: {} to {}", sectionId, duration);
    }

    @Override
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    @Transactional
    public void deleteSection(Long sectionId) {
        log.debug("Deleting section with ID: {}", sectionId);
        User user = userService.getAuthenticatedUser();
        SectionDeletionContext context = new SectionDeletionContext(sectionId, user);
        OperationHandler<SectionDeletionContext> operationHandler = sectionDeletionChainBuilder.build();
        operationHandler.handle(context);
        log.info("Section with ID: {} deleted successfully", sectionId);
    }

    private Section createSectionProcess(CreateSectionRequest createSectionRequest, Long courseId, User user) {
        SectionCreationContext context = new SectionCreationContext(createSectionRequest, courseId, user);
        OperationHandler<SectionCreationContext> operationHandler = sectionCreationChainBuilder.build();
        operationHandler.handle(context);
        return context.getCreatedSection();
    }
}
