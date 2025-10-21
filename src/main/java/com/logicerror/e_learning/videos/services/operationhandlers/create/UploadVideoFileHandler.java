package com.logicerror.e_learning.videos.services.operationhandlers.create;

import com.logicerror.e_learning.courses.entities.Course;
import com.logicerror.e_learning.sections.entities.Section;
import com.logicerror.e_learning.videos.entities.Video;
import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.videos.exceptions.VideoCreationFailedException;
import com.logicerror.e_learning.services.FileManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class UploadVideoFileHandler extends BaseVideoCreationHandler {
    private final FileManagementService fileManagementService;

    @Override
    protected void processRequest(VideoCreationContext context) {
        logger.debug("Uploading video file for user: {}", context.getUser().getUsername());

        User teacher = context.getUser();
        Video video = context.getVideo();
        MultipartFile videoFile = context.getVideoFile();

        String directory = buildFilePath(teacher, video, videoFile);
        String filePath = storeFile(videoFile, directory);
        video.setUrl(filePath);

        logger.debug("Video file uploaded successfully to: {}", filePath);
    }

    private String buildFilePath(User teacher, Video video, MultipartFile videoFile) {
        Section videoSection = video.getSection();
        Course videoCourse = video.getCourse();
        return fileManagementService.getCourseVideosPath()
                + File.separator
                + teacher.getUsername()
                + File.separator
                + videoCourse.getTitle()
                + File.separator
                + videoSection.getTitle()
                + File.separator
                + video.getTitle()
                + File.separator
                + videoFile.getOriginalFilename();
    }

    private String storeFile(MultipartFile videoFile, String filePath) {
        try (InputStream input = videoFile.getInputStream()) {
            return fileManagementService.uploadFile(input, filePath);
        } catch (IOException e) {
            throw new VideoCreationFailedException("Failed to store video file: " + e.getMessage());
        }
    }
}
