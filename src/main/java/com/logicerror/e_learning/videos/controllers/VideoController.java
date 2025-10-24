package com.logicerror.e_learning.videos.controllers;

import com.logicerror.e_learning.controllers.responses.ApiResponse;
import com.logicerror.e_learning.videos.dtos.VideoDto;
import com.logicerror.e_learning.videos.entities.Video;
import com.logicerror.e_learning.videos.requests.BatchCreateVideoRequest;
import com.logicerror.e_learning.videos.requests.CreateVideoRequest;
import com.logicerror.e_learning.videos.requests.UpdateVideoRequest;
import com.logicerror.e_learning.videos.services.VideoService;
import com.logicerror.e_learning.videos.services.VideoStreamingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${api.base-path}/videos")
@RequiredArgsConstructor
public class VideoController {
    private final VideoService videoService;
    private final VideoStreamingService videoStreamingService;

    // Get Methods
    // Get by ID
    @GetMapping("/{videoId}")
    public ResponseEntity<ApiResponse<VideoDto>> getVideoById(@PathVariable Long videoId) {
        VideoDto videoDto = videoService.convertToDto(videoService.getVideoById(videoId));
        return ResponseEntity.ok(new ApiResponse<>("Video fetched successfully", videoDto));
    }
    // Get by title and course
    @GetMapping("/course/{courseId}/title/{title}")
    public ResponseEntity<ApiResponse<VideoDto>> getVideoByTitleAndCourse(@PathVariable Long courseId, @PathVariable String title) {
        VideoDto videoDto = videoService.convertToDto(videoService.getVideoByTitleAndCourse(title, courseId));
        return ResponseEntity.ok(new ApiResponse<>("Video fetched successfully", videoDto));
    }
    // Get by title and section
    @GetMapping("/section/{sectionId}/title/{title}")
    public ResponseEntity<ApiResponse<VideoDto>> getVideoByTitleAndSection(Long sectionId, String title) {
        VideoDto videoDto = videoService.convertToDto(videoService.getVideoByTitleAndSection(title, sectionId));
        return ResponseEntity.ok(new ApiResponse<>("Video fetched successfully", videoDto));
    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<VideoDto>> createVideo(@Valid @RequestPart("details") CreateVideoRequest request,
                                                             @RequestPart("videoFile") MultipartFile videoFile,
                                                             @RequestParam Long courseId,
                                                             @RequestParam Long sectionId) {
        VideoDto videoDto = videoService.convertToDto(videoService.createVideo(request, courseId, sectionId, videoFile));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Video created successfully", videoDto));
    }

    @PostMapping(value = "/batch", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<List<VideoDto>>> batchCreateVideos(@Valid @RequestPart("details") BatchCreateVideoRequest request,
                                                                         @RequestParam Long courseId,
                                                                         @RequestParam Long sectionId,
                                                                         MultipartHttpServletRequest multipartRequest){
        Map<String, MultipartFile> fileMap = new HashMap<>();
        for(Map.Entry<String, MultipartFile> entry : multipartRequest.getFileMap().entrySet()){
            if(!entry.getKey().equals("details")) {
                fileMap.put(entry.getKey(), entry.getValue());
            }
        }
        List<Video> videos = videoService.batchCreateVideos(request, courseId, sectionId, fileMap);
        List<VideoDto> videosDto = videos.stream()
                .map(videoService::convertToDto)
                .toList();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Videos created successfully", videosDto));
    }

    // Patch
    @PatchMapping(value = "/{videoId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<VideoDto>> updateVideo(@Valid @RequestPart("details") UpdateVideoRequest request,
                                                             @RequestPart(value = "videoFile", required = false) MultipartFile videoFile,
                                                             @PathVariable Long videoId) {
        Video updateVideo = videoService.updateVideo(request, videoFile, videoId);
        VideoDto videoDto = videoService.convertToDto(updateVideo);
        return ResponseEntity.ok(new ApiResponse<>("Video updated successfully", videoDto));
    }

    // Delete
    @DeleteMapping("/{videoId}")
    public ResponseEntity<ApiResponse<Void>> deleteVideo(@PathVariable Long videoId) {
        videoService.deleteVideo(videoId);
        return ResponseEntity.ok(new ApiResponse<>("Video deleted successfully", null));
    }
}
