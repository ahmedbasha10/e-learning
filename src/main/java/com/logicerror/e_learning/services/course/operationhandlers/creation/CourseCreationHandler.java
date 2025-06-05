package com.logicerror.e_learning.services.course.operationhandlers.creation;

import com.logicerror.e_learning.entities.course.Course;
import com.logicerror.e_learning.entities.course.Section;
import com.logicerror.e_learning.exceptions.course.CourseCreationFailedException;
import com.logicerror.e_learning.mappers.CourseMapper;
import com.logicerror.e_learning.mappers.SectionMapper;
import com.logicerror.e_learning.repositories.CourseRepository;
import com.logicerror.e_learning.repositories.SectionRepository;
import com.logicerror.e_learning.requests.course.CreateCourseRequest;
import com.logicerror.e_learning.requests.course.section.CreateSectionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CourseCreationHandler extends BaseCourseCreationHandler {
    private final CourseRepository courseRepository;
    private final SectionRepository sectionRepository;
    private final CourseMapper courseMapper;
    private final SectionMapper sectionMapper;
    
    
    @Override
    protected void processRequest(CourseCreationContext context) {
        CreateCourseRequest request = context.getRequest();
        logger.info("Creating new course with title: {}", request.getTitle());
        Course course = courseMapper.createCourseRequestToCourse(request);
        Course savedCourse = courseRepository.save(course);
        
        if (savedCourse.getId() == null) {
            logger.error("Course creation failed: could not generate ID");
            throw new CourseCreationFailedException("Failed to create course");
        }
        context.setCourse(savedCourse);

        List<CreateSectionRequest> sectionsRequest = request.getSections();
        if(sectionsRequest != null) {
            for(CreateSectionRequest sectionRequest : sectionsRequest) {
                logger.info("Creating section with title: {}", sectionRequest.getTitle());
                // Assuming you have a method to create sections
                Section section = sectionMapper.createSectionRequestToSection(sectionRequest);
                // createSection(savedCourse, sectionRequest);
                section.setCourse(savedCourse);

                Section savedSection = sectionRepository.save(section);
                savedCourse.addSection(savedSection);
            }
        }


        logger.info("Successfully created course with ID: {}", savedCourse.getId());
    }

    

}
