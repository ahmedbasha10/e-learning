package com.logicerror.e_learning.services.course.operationhandlers.creation;

import com.logicerror.e_learning.config.CourseProperties;
import com.logicerror.e_learning.entities.course.Course;
import com.logicerror.e_learning.exceptions.course.CourseCreationFailedException;
import com.logicerror.e_learning.mappers.CourseMapper;
import com.logicerror.e_learning.repositories.CourseRepository;
import com.logicerror.e_learning.requests.course.CreateCourseRequest;
import com.logicerror.e_learning.services.FileManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CourseCreationHandler extends BaseCourseCreationHandler {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final FileManagementService fileManagementService;
    private final CourseProperties courseProperties;

    
    @Override
    protected void processRequest(CourseCreationContext context) {
        CreateCourseRequest request = context.getRequest();

        logger.info("Creating new course with title: {}", request.getTitle());

        Course savedCourse = createCourse(context, request);

        checkCourseCreationState(savedCourse);

        context.setCourse(savedCourse);

        logger.info("Successfully created course with ID: {}", savedCourse.getId());
    }

    private Course createCourse(CourseCreationContext context, CreateCourseRequest request) {
        Course course = courseMapper.createCourseRequestToCourse(request);
        setCourseThumbnail(course, context.getThumbnail());
        course.setDuration(0);
        return courseRepository.save(course);
    }

    private void checkCourseCreationState(Course savedCourse) {
        if (savedCourse.getId() == null) {
            logger.error("Course creation failed: could not generate ID");
            throw new CourseCreationFailedException("Failed to create course");
        }
    }

    private void setCourseThumbnail(Course course, MultipartFile thumbnail) {
        course.setImageUrl(getThumbnailUrl(course, thumbnail));
    }

    private String getThumbnailUrl(Course course, MultipartFile thumbnail) {
        if (thumbnail != null && !thumbnail.isEmpty()) {
            try {
                return fileManagementService.uploadFile(thumbnail.getInputStream(), buildThumbnailPath(course.getTitle(), thumbnail.getOriginalFilename()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            logger.warn("No thumbnail provided, setting default image for course");
            return courseProperties.getDefaultThumbnail();
        }
    }

    private String buildThumbnailPath(String courseTitle, String originalFilename) {
        return fileManagementService.getCourseImagesPath()
                + File.separator
                + courseTitle
                + File.separator
                + originalFilename;
    }


}
