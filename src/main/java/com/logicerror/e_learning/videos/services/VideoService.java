package com.logicerror.e_learning.videos.services;

import com.logicerror.e_learning.videos.dtos.VideoDto;
import com.logicerror.e_learning.videos.entities.Video;
import com.logicerror.e_learning.videos.requests.BatchCreateVideoRequest;
import com.logicerror.e_learning.videos.requests.CreateVideoRequest;
import com.logicerror.e_learning.videos.requests.UpdateVideoRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface VideoService {
    VideoDto getVideoById(Long id);
    VideoDto getVideoByTitleAndCourse(String title, Long courseId);
    VideoDto getVideoByTitleAndSection(String title, Long sectionId);
    List<VideoDto> getCourseVideos(Long courseId);

    VideoDto createVideo(CreateVideoRequest request, Long courseId ,Long sectionId, MultipartFile videoFile);
    List<VideoDto> batchCreateVideos(BatchCreateVideoRequest request, Long courseId, Long sectionId,
                                  Map<String, MultipartFile> fileMap);

    VideoDto updateVideo(UpdateVideoRequest request, MultipartFile videoFile, Long videoId);

    void deleteVideo(Long videoId);
}
