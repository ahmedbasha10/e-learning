package com.logicerror.e_learning.services.section.operationhandlers.create;

import com.logicerror.e_learning.entities.course.Course;
import com.logicerror.e_learning.entities.course.Section;
import com.logicerror.e_learning.exceptions.section.SectionCreationFailedException;
import com.logicerror.e_learning.mappers.SectionMapper;
import com.logicerror.e_learning.repositories.SectionRepository;
import com.logicerror.e_learning.requests.course.section.CreateSectionRequest;
import com.logicerror.e_learning.services.course.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class SectionCreationHandler extends BaseSectionCreationHandler {
    private final SectionMapper sectionMapper;
    private final SectionRepository sectionRepository;
    private final CourseService courseService;

    @Override
    protected void processRequest(SectionCreationContext context) {
        log.debug("Creating new section with title: {}", context.getRequest().getTitle());
        CreateSectionRequest request = context.getRequest();
        Section section = sectionMapper.createSectionRequestToSection(request);
        Course course = courseService.getCourseById(context.getCourseId());
        section.setCourse(course);
        section.setDuration(0);
        Section savedSection = sectionRepository.save(section);
        context.setCreatedSection(savedSection);
        if (savedSection.getId() == null) {
            log.error("Section creation failed: could not generate ID");
            throw new SectionCreationFailedException("Failed to create section");
        }
        log.info("Successfully created section with ID: {}", savedSection.getId());
    }
}
