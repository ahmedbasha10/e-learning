package com.logicerror.e_learning.services.course.operationhandlers.creation;

import com.logicerror.e_learning.config.StorageProperties;
import com.logicerror.e_learning.entities.course.Course;
import com.logicerror.e_learning.entities.course.Section;
import com.logicerror.e_learning.exceptions.course.CourseCreationFailedException;
import com.logicerror.e_learning.mappers.CourseMapper;
import com.logicerror.e_learning.mappers.SectionMapper;
import com.logicerror.e_learning.repositories.CourseRepository;
import com.logicerror.e_learning.repositories.SectionRepository;
import com.logicerror.e_learning.requests.course.CreateCourseRequest;
import com.logicerror.e_learning.requests.course.section.CreateSectionRequest;
import com.logicerror.e_learning.services.FileManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CourseCreationHandler extends BaseCourseCreationHandler {
    private final CourseRepository courseRepository;
    private final SectionRepository sectionRepository;
    private final CourseMapper courseMapper;
    private final SectionMapper sectionMapper;
    private final FileManagementService fileManagementService;
    private final StorageProperties storageProperties;
    
    
    @Override
    protected void processRequest(CourseCreationContext context) {
        CreateCourseRequest request = context.getRequest();
        logger.info("Creating new course with title: {}", request.getTitle());
        Course course = courseMapper.createCourseRequestToCourse(request);
        setCourseThumbnail(course, context.getThumbnail());
        course.setDuration(0);
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
                section.setDuration(0);

                Section savedSection = sectionRepository.save(section);
                savedCourse.addSection(savedSection);
            }
        }


        logger.info("Successfully created course with ID: {}", savedCourse.getId());
    }

    private void setCourseThumbnail(Course course, MultipartFile thumbnail) {
        if (thumbnail != null && !thumbnail.isEmpty()) {
            try {
                String thumbnailUrl = fileManagementService.uploadFile(thumbnail.getInputStream(), buildThumbnailPath(course.getTitle(), thumbnail.getOriginalFilename()));
                course.setImageUrl(thumbnailUrl);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            logger.warn("No thumbnail provided, setting default image for course");
            course.setImageUrl("storage/images/courses/default.png");
        }
    }

    private String buildThumbnailPath(String courseTitle, String originalFilename) {
        return storageProperties.getImagePath()
                + File.separator
                + "courses"
                + File.separator
                + courseTitle
                + File.separator
                + originalFilename;
    }


}
