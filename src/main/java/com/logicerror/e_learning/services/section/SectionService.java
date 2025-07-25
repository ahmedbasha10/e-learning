package com.logicerror.e_learning.services.section;

import com.logicerror.e_learning.dto.SectionDto;
import com.logicerror.e_learning.entities.course.Section;
import com.logicerror.e_learning.entities.course.Video;
import com.logicerror.e_learning.entities.teacher.TeacherCoursesKey;
import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.exceptions.section.SectionNotFoundException;
import com.logicerror.e_learning.mappers.SectionMapper;
import com.logicerror.e_learning.repositories.SectionRepository;
import com.logicerror.e_learning.repositories.TeacherCoursesRepository;
import com.logicerror.e_learning.repositories.VideoRepository;
import com.logicerror.e_learning.requests.course.section.BatchCreateSectionRequest;
import com.logicerror.e_learning.requests.course.section.CreateSectionRequest;
import com.logicerror.e_learning.requests.course.section.UpdateSectionRequest;
import com.logicerror.e_learning.services.OperationHandler;
import com.logicerror.e_learning.services.section.operationhandlers.create.SectionCreationChainBuilder;
import com.logicerror.e_learning.services.section.operationhandlers.create.SectionCreationContext;
import com.logicerror.e_learning.services.section.operationhandlers.update.SectionUpdateChainBuilder;
import com.logicerror.e_learning.services.section.operationhandlers.update.SectionUpdateContext;
import com.logicerror.e_learning.services.user.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SectionService implements ISectionService{
    private final IUserService userService;
    private final SectionRepository sectionRepository;
    private final VideoRepository videoRepository;
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
    public Page<Video> getSectionVideos(Long sectionId, Pageable pageable) {
        Assert.notNull(sectionId, "Section ID must not be null");
        Assert.notNull(pageable, "Pageable must not be null");
        log.debug("Fetching videos for section with ID: {}", sectionId);
        return videoRepository.findAllBySectionId(sectionId, pageable);
    }

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

    private Section createSectionProcess(CreateSectionRequest createSectionRequest, Long courseId, User user) {
        SectionCreationContext context = new SectionCreationContext(createSectionRequest, courseId, user);
        OperationHandler<SectionCreationContext> operationHandler = sectionCreationChainBuilder.build();
        operationHandler.handle(context);
        return context.getCreatedSection();
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
    @Transactional
    public void updateSectionDuration(Long sectionId) {
        Assert.notNull(sectionId, "Section must not be null");
        log.debug("Updating duration for section with ID: {}", sectionId);
        Section section = getSectionById(sectionId);
        Long totalDuration = videoRepository.findAllBySectionId(sectionId)
                .stream()
                .mapToLong(Video::getDuration)
                .sum();
        section.setDuration(totalDuration.intValue());
        sectionRepository.save(section);
        log.info("Updated duration for section with ID: {} to {}", section.getId(), totalDuration);
    }

    @Override
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    @Transactional
    public void deleteSection(Long sectionId) {
        log.debug("Deleting section with ID: {}", sectionId);
        User user = userService.getAuthenticatedUser();
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
