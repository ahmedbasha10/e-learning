package com.logicerror.e_learning.controllers;

import com.logicerror.e_learning.services.video.VideoStreamingService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Path;

@RestController
@RequestMapping("/storage/videos")
@RequiredArgsConstructor
public class StreamingVideoController {
    private final VideoStreamingService videoStreamingService;

    @GetMapping("/{teacherName}/{courseName}/{sectionName}/{videoId}/{fileName}")
    public ResponseEntity<Resource> streamVideo(@PathVariable String teacherName,
                                                @PathVariable String courseName,
                                                @PathVariable String sectionName,
                                                @PathVariable Long videoId,
                                                @PathVariable String fileName,
                                                @RequestHeader(value = "Range", required = false) String rangeHeader) throws IOException {

        Path videoPath = Path.of(teacherName, courseName, sectionName, String.valueOf(videoId), fileName);

        return videoStreamingService.streamVideo(videoPath, rangeHeader);
    }
}
