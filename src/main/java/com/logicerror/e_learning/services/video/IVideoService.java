package com.logicerror.e_learning.services.video;

import com.logicerror.e_learning.dto.VideoDto;
import com.logicerror.e_learning.entities.course.Video;
import com.logicerror.e_learning.requests.course.video.CreateVideoRequest;
import com.logicerror.e_learning.requests.course.video.UpdateVideoRequest;
import org.springframework.web.multipart.MultipartFile;

public interface IVideoService {
    // Get
    Video getVideoById(Long id);
    Video getVideoByTitleAndCourse(String title, Long courseId);
    Video getVideoByTitleAndSection(String title, Long sectionId);

    // Post
    Video createVideo(CreateVideoRequest request, Long sectionId, MultipartFile videoFile);

    // Patch
    Video updateVideo(UpdateVideoRequest request, Long videoId);

    // Delete
    void deleteVideo(Long videoId);

    VideoDto convertToDto(Video video);
}
