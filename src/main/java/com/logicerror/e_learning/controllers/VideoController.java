package com.logicerror.e_learning.controllers;

import com.logicerror.e_learning.controllers.responses.ApiResponse;
import com.logicerror.e_learning.dto.VideoDto;
import com.logicerror.e_learning.requests.course.video.CreateVideoRequest;
import com.logicerror.e_learning.services.video.IVideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("${api.base-path}/videos")
@RequiredArgsConstructor
public class VideoController {
    private final IVideoService videoService;

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


    // Post
    // To create a video, send video metadata in the request body and the section ID as a request parameter.
    // send video file is a request param multipart file

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<VideoDto>> createVideo(@RequestPart("details") CreateVideoRequest request,
                                                             @RequestPart("videoFile") MultipartFile videoFile,
                                                             @RequestParam Long sectionId) {
        VideoDto videoDto = videoService.convertToDto(videoService.createVideo(request, sectionId, videoFile));
        return ResponseEntity.ok(new ApiResponse<>("Video created successfully", videoDto));
    }

    // Patch

    // Delete
}
