package com.logicerror.e_learning.services.video.operationhandlers.update;

import com.logicerror.e_learning.entities.course.Course;
import com.logicerror.e_learning.entities.course.Section;
import com.logicerror.e_learning.entities.course.Video;
import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.exceptions.video.VideoCreationFailedException;
import com.logicerror.e_learning.services.FileManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class VideoContentUpdateHandler extends BaseVideoUpdateHandler{
    private final FileManagementService fileManagementService;

    @Override
    protected void processRequest(VideoUpdateContext context) {
        logger.debug("Updating video content for video: {}", context.getVideo().getId());
        if(context.getVideoFile() == null) {
            logger.debug("No video file provided, skipping content update for video: {}", context.getVideo().getId());
            return;
        }
        Video video = context.getVideo();
        context.setOldVideoFilePath(video.getUrl());
        MultipartFile videoFile = context.getVideoFile();
        String videoFileDirectory = buildFilePath(context.getUser(), video, videoFile);
        String filePath = storeFile(videoFile, videoFileDirectory);
        video.setUrl(filePath);
        logger.debug("Video content updated successfully for video: {}", video.getId());
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
