package com.logicerror.e_learning.videos.services;

import com.logicerror.e_learning.config.StorageProperties;
import com.logicerror.e_learning.services.FileManagementService;
import com.logicerror.e_learning.videos.services.models.RangeRequest;
import com.logicerror.e_learning.videos.services.models.VideoFileInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class VideoStreamingService {
    private final StorageProperties storageProperties;
    private final FileManagementService fileManagementService;

    public ResponseEntity<Resource> streamVideo(Path videoPath, String rangeHeader) throws IOException {
        // Get video metadata
        VideoFileInfo fileInfo = validateAndGetFileInfo(videoPath);
        // Handle range request
        if (rangeHeader != null && rangeHeader.startsWith("bytes=")) {
            return handleRangeRequest(fileInfo, rangeHeader);
        }
        // Return full video
        return buildFullVideoResponse(fileInfo);
    }

    private VideoFileInfo validateAndGetFileInfo(Path videoPath) throws IOException {
        String fullPath = storageProperties.getVideoPath() + File.separator + videoPath.toString();
        Resource videoResource = fileManagementService.loadFileAsResource(fullPath);
        String contentType = determineContentType(videoResource.getFile());

        return new VideoFileInfo(videoResource, videoResource.contentLength(), contentType);
    }

    private ResponseEntity<Resource> handleRangeRequest(VideoFileInfo fileInfo, String rangeHeader) throws IOException {
        RangeRequest range = parseRangeHeader(rangeHeader, fileInfo.contentLength());

        if (!range.isValid()) {
            return ResponseEntity.status(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE)
                    .header("Content-Range", "bytes */" + fileInfo.contentLength())
                    .build();
        }

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .header("Content-Range", range.getContentRangeHeader(fileInfo.contentLength()))
                .header("Accept-Ranges", "bytes")
                .contentLength(range.getLength())
                .contentType(MediaType.parseMediaType(fileInfo.contentType()))
                .body(createRangeResource(fileInfo.resource(), range));
    }

    private ResponseEntity<Resource> buildFullVideoResponse(VideoFileInfo fileInfo) {
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileInfo.contentType()))
                .header("Accept-Ranges", "bytes")
                .contentLength(fileInfo.contentLength())
                .body(fileInfo.resource());
    }

    private RangeRequest parseRangeHeader(String rangeHeader, long contentLength) {
        String[] ranges = rangeHeader.substring(6).split("-");
        long start = Long.parseLong(ranges[0]);
        long end = (ranges.length > 1 && !ranges[1].isEmpty())
                ? Long.parseLong(ranges[1])
                : contentLength - 1;

        return new RangeRequest(start, end, contentLength);
    }

    private Resource createRangeResource(Resource originalResource, RangeRequest range) throws IOException {
        InputStream inputStream = originalResource.getInputStream();
        inputStream.skip(range.start());
        return new InputStreamResource(inputStream) {
            @Override
            public long contentLength() {
                return range.getLength();
            }
        };
    }

    private String determineContentType(File file) {
        String fileName = file.getName().toLowerCase();
        if (fileName.endsWith(".mp4")) return "video/mp4";
        if (fileName.endsWith(".webm")) return "video/webm";
        if (fileName.endsWith(".avi")) return "video/avi";
        return "video/mp4"; // default
    }
}
