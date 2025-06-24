package com.logicerror.e_learning.controllers;

import com.logicerror.e_learning.config.StorageProperties;
import com.logicerror.e_learning.services.FileManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;

@RestController
@RequestMapping("/storage/images")
@RequiredArgsConstructor
public class ImagesController {
    private final FileManagementService fileManagementService;
    private final StorageProperties storageProperties;

    @GetMapping("/courses/{fileName}")
    public ResponseEntity<Resource> getCourseImage(@PathVariable String fileName) {
        Path filePath = Path.of(storageProperties.getImagePath(), "courses", fileName);
        Resource resource = fileManagementService.loadFileAsResource(filePath.toString());
        return ResponseEntity.ok()
                .header("Content-Disposition", "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
