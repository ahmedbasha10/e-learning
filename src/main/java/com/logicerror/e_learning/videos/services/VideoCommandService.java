package com.logicerror.e_learning.videos.services;

import com.logicerror.e_learning.videos.entities.Video;
import com.logicerror.e_learning.videos.requests.BatchCreateVideoRequest;
import com.logicerror.e_learning.videos.requests.CreateVideoRequest;
import com.logicerror.e_learning.videos.requests.UpdateVideoRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface VideoCommandService {
    Video createVideo(CreateVideoRequest request, Long courseId , Long sectionId, MultipartFile videoFile);
    List<Video> batchCreateVideos(BatchCreateVideoRequest request, Long courseId, Long sectionId, Map<String, MultipartFile> fileMap);
    Video updateVideo(UpdateVideoRequest request, MultipartFile videoFile, Long videoId);
    void deleteVideo(Long videoId);
}
