package com.logicerror.e_learning.videos.services;

import com.logicerror.e_learning.videos.dtos.VideoDto;
import com.logicerror.e_learning.videos.entities.Video;
import com.logicerror.e_learning.videos.requests.BatchCreateVideoRequest;
import com.logicerror.e_learning.videos.requests.CreateVideoRequest;
import com.logicerror.e_learning.videos.requests.UpdateVideoRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class DefaultVideoService implements VideoService {
    private final VideoQueryService videoQueryService;
    private final VideoCommandService videoCommandService;
    private final VideoDTOService videoDTOService;


    @Override
    public VideoDto getVideoById(Long id) {
        Video video = videoQueryService.getVideoById(id);
        return videoDTOService.convertToDto(video);
    }

    @Override
    public VideoDto getVideoByTitleAndCourse(String title, Long courseId) {
        Video video = videoQueryService.getVideoByTitleAndCourse(title, courseId);
        return videoDTOService.convertToDto(video);
    }

    @Override
    public VideoDto getVideoByTitleAndSection(String title, Long sectionId) {
        Video video = videoQueryService.getVideoByTitleAndSection(title, sectionId);
        return videoDTOService.convertToDto(video);
    }

    @Override
    public List<VideoDto> getCourseVideos(Long courseId) {
        return videoQueryService.getCourseVideos(courseId)
                .stream()
                .map(videoDTOService::convertToDto)
                .toList();
    }

    @Override
    public VideoDto createVideo(CreateVideoRequest request, Long courseId, Long sectionId, MultipartFile videoFile) {
        Video video = videoCommandService.createVideo(request, courseId, sectionId, videoFile);
        return videoDTOService.convertToDto(video);
    }


    @Override
    public List<VideoDto> batchCreateVideos(BatchCreateVideoRequest request, Long courseId, Long sectionId, Map<String, MultipartFile> fileMap) {
        return videoCommandService.batchCreateVideos(request, courseId, sectionId, fileMap)
                .stream()
                .map(videoDTOService::convertToDto)
                .toList();
    }



    @Override
    public VideoDto updateVideo(UpdateVideoRequest request, MultipartFile videoFile, Long videoId) {
        Video video = videoCommandService.updateVideo(request, videoFile, videoId);
        return videoDTOService.convertToDto(video);
    }


    @Override
    public void deleteVideo(Long videoId) {
        videoCommandService.deleteVideo(videoId);
    }
}
