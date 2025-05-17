package com.logicerror.e_learning.services.section;

import com.logicerror.e_learning.dto.SectionDto;
import com.logicerror.e_learning.entities.course.Course;
import com.logicerror.e_learning.entities.course.Section;
import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.exceptions.section.SectionNotFoundException;
import com.logicerror.e_learning.mappers.SectionMapper;
import com.logicerror.e_learning.repositories.SectionRepository;
import com.logicerror.e_learning.requests.course.section.CreateSectionRequest;
import com.logicerror.e_learning.services.OperationHandler;
import com.logicerror.e_learning.services.course.CourseService;
import com.logicerror.e_learning.services.section.operationhandlers.create.SectionCreationChainBuilder;
import com.logicerror.e_learning.services.section.operationhandlers.create.SectionCreationContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SectionService implements ISectionService{
    private final SectionRepository sectionRepository;
    private final SectionMapper sectionMapper;
    private final SectionCreationChainBuilder sectionCreationChainBuilder;
    private final CourseService courseService;


    @Override
    public Section getSectionById(Long sectionId) {
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new SectionNotFoundException("Section not found with id: " + sectionId));

        return section;
    }

    @Override
    public Section getSectionByTitle(String title) {
        Section section = sectionRepository.findByTitle(title)
                .orElseThrow(() -> new SectionNotFoundException("Section not found with title: " + title));

        return section;
    }

    @Override
    public Section createSection(CreateSectionRequest createSectionRequest, Long courseId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        OperationHandler<SectionCreationContext> operationHandler = sectionCreationChainBuilder.build();
        Course course = courseService.getCourseById(courseId);
        SectionCreationContext context = new SectionCreationContext(createSectionRequest, course, user);
        operationHandler.handle(context);
        return context.getCreatedSection();
    }

    @Override
    public SectionDto convertToDto(Section section) {
        return sectionMapper.sectionToSectionDto(section);
    }
}
