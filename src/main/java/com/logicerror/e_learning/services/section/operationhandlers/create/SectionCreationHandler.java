package com.logicerror.e_learning.services.section.operationhandlers.create;

import com.logicerror.e_learning.courses.entities.Course;
import com.logicerror.e_learning.entities.course.Section;
import com.logicerror.e_learning.exceptions.section.SectionCreationFailedException;
import com.logicerror.e_learning.mappers.SectionMapper;
import com.logicerror.e_learning.repositories.SectionRepository;
import com.logicerror.e_learning.requests.course.section.CreateSectionRequest;
import com.logicerror.e_learning.courses.services.CourseQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class SectionCreationHandler extends BaseSectionCreationHandler {
    private final SectionMapper sectionMapper;
    private final SectionRepository sectionRepository;
    private final CourseQueryService courseService;

    @Override
    protected void processRequest(SectionCreationContext context) {
        log.debug("Creating new section with title: {}", context.getRequest().getTitle());
        Section savedSection = createSection(context);
        checkSectionCreationStatus(savedSection);
        context.setCreatedSection(savedSection);
        log.info("Successfully created section with ID: {}", savedSection.getId());
    }

    private Section createSection(SectionCreationContext context) {
        CreateSectionRequest request = context.getRequest();
        Section section = sectionMapper.createSectionRequestToSection(request);
        Course course = new Course();
        course.setId(context.getCourseId());
        section.setCourse(course);
        section.setDuration(0);
        return sectionRepository.save(section);
    }

    private void checkSectionCreationStatus(Section savedSection) {
        if (savedSection.getId() == null) {
            log.error("Section creation failed: could not generate ID");
            throw new SectionCreationFailedException("Failed to create section");
        }
    }
}
