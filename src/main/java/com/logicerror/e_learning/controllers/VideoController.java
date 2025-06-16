package com.logicerror.e_learning.controllers;

import com.logicerror.e_learning.controllers.responses.ApiResponse;
import com.logicerror.e_learning.dto.VideoDto;
import com.logicerror.e_learning.entities.course.Video;
import com.logicerror.e_learning.requests.course.video.CreateVideoRequest;
import com.logicerror.e_learning.requests.course.video.UpdateVideoRequest;
import com.logicerror.e_learning.services.video.IVideoService;
import com.logicerror.e_learning.services.video.VideoStreamingService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("${api.base-path}/videos")
@RequiredArgsConstructor
public class VideoController {
    private final IVideoService videoService;
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

    @GetMapping("/stream/{videoId}")
    public ResponseEntity<Resource> streamVideo(@PathVariable Long videoId,
                                                @RequestHeader(value = "Range", required = false) String rangeHeader) throws IOException {
        return videoStreamingService.streamVideo(videoId, rangeHeader);
    }



    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<VideoDto>> createVideo(@RequestPart("details") CreateVideoRequest request,
                                                             @RequestPart("videoFile") MultipartFile videoFile,
                                                             @RequestParam Long sectionId) {
        VideoDto videoDto = videoService.convertToDto(videoService.createVideo(request, sectionId, videoFile));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Video created successfully", videoDto));
    }

    // Patch
    @PatchMapping(value = "/{videoId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<VideoDto>> updateVideo(@RequestPart("details") UpdateVideoRequest request,
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
