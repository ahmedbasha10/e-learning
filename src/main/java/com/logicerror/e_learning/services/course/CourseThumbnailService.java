package com.logicerror.e_learning.services.course;

import com.logicerror.e_learning.config.CourseProperties;
import com.logicerror.e_learning.entities.course.Course;
import com.logicerror.e_learning.exceptions.files.FileException;
import com.logicerror.e_learning.services.FileManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class CourseThumbnailService {

    private final FileManagementService fileManagementService;
    private final CourseProperties courseProperties;

    public void setCourseThumbnail(Course course, MultipartFile thumbnail) {
        course.setImageUrl(getThumbnailUrl(course, thumbnail));
    }

    private String getThumbnailUrl(Course course, MultipartFile thumbnail) {
        if (thumbnail != null && !thumbnail.isEmpty()) {
            try {
                return fileManagementService.uploadFile(thumbnail.getInputStream(), buildThumbnailPath(course.getTitle(), thumbnail.getOriginalFilename()));
            } catch (IOException e) {
                throw new FileException(e.getMessage());
            }
        } else {
            log.warn("No thumbnail provided, setting default image for course");
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
