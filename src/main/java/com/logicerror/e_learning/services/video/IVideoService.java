package com.logicerror.e_learning.services.video;

import com.logicerror.e_learning.dto.VideoDto;
import com.logicerror.e_learning.entities.course.Video;
import com.logicerror.e_learning.requests.course.video.BatchCreateVideoRequest;
import com.logicerror.e_learning.requests.course.video.CreateVideoRequest;
import com.logicerror.e_learning.requests.course.video.UpdateVideoRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface IVideoService {
    // Get
    Video getVideoById(Long id);
    Video getVideoByTitleAndCourse(String title, Long courseId);
    Video getVideoByTitleAndSection(String title, Long sectionId);
    List<Video> getCourseVideos(Long courseId);
    int countVideosInCourse(Long courseId);
    // Post
    Video createVideo(CreateVideoRequest request, Long courseId ,Long sectionId, MultipartFile videoFile);
    List<Video> batchCreateVideos(BatchCreateVideoRequest request, Long courseId, Long sectionId,
                                  Map<String, MultipartFile> fileMap);

    // Patch
    Video updateVideo(UpdateVideoRequest request, MultipartFile videoFile, Long videoId);

    // Delete
    void deleteVideo(Long videoId);

    VideoDto convertToDto(Video video);



}
