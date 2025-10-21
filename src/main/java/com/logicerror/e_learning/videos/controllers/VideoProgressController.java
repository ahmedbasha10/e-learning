package com.logicerror.e_learning.videos.controllers;

import com.logicerror.e_learning.controllers.responses.ApiResponse;
import com.logicerror.e_learning.courses.dtos.CourseProgressDTO;
import com.logicerror.e_learning.videos.requests.VideoCompletionRequest;
import com.logicerror.e_learning.videos.services.VideoProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.base-path}/video-progress")
@RequiredArgsConstructor
public class VideoProgressController {

    private final VideoProgressService videoProgressService;

    @GetMapping("/course/{courseId}")
    public ResponseEntity<ApiResponse<CourseProgressDTO>> getCourseProgress(@PathVariable Long courseId) {
        CourseProgressDTO progress = videoProgressService.getCourseProgress(courseId);
        return ResponseEntity.ok(new ApiResponse<>("Course progress fetched successfully", progress));
    }

    @PostMapping("/complete")
    public ResponseEntity<ApiResponse<Void>> markVideoAsCompleted(@RequestBody VideoCompletionRequest request) {
        videoProgressService.markVideoAsCompleted(request.getVideoId(), request.getWatchedDuration());
        return ResponseEntity.ok(new ApiResponse<>("Video marked as completed", null));
    }
}
