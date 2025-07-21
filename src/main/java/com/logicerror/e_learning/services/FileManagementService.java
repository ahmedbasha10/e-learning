package com.logicerror.e_learning.services;

import com.logicerror.e_learning.config.StorageProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;


@Service
public class FileManagementService {
    private final Path rootLocation;
    private final StorageProperties storageProperties;

    public FileManagementService(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
        this.rootLocation = Path.of(storageProperties.getBasePath());
    }

    public Resource loadFileAsResource(String filePath) {
        if (filePath == null || filePath.isBlank()) {
            throw new IllegalArgumentException("File path must not be null or empty");
        }

        Path file = rootLocation.resolve(filePath).normalize().toAbsolutePath();
        if (!Files.exists(file)) {
            throw new RuntimeException("File not found: " + filePath);
        }

        try {
            return new FileSystemResource(file);
        } catch (Exception e) {
            throw new RuntimeException("Could not read file: " + filePath, e);
        }
    }

    public String uploadFile(InputStream inputStream, String filePath) throws IOException {
        if (inputStream == null || filePath == null || filePath.isBlank()) {
            throw new IllegalArgumentException("Input stream and file name must not be null or empty");
        }

        Path destinationFile = rootLocation.resolve(filePath).normalize();

        if (!destinationFile.startsWith(rootLocation)) {
            throw new SecurityException("Cannot store file outside current directory.");
        }

        Files.createDirectories(destinationFile.getParent());
        Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);

        return destinationFile.toString();
    }


    public void deleteFile(String url) {
        if (url == null || url.isBlank()) {
            throw new IllegalArgumentException("File URL must not be null or empty");
        }

        Path filePath = Path.of(url);
        try {
            Files.deleteIfExists(filePath);
            Files.deleteIfExists(filePath.getParent());
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file: " + url, e);
        }
    }

    public String getCourseImagesPath() {
        return storageProperties.getImagePath() + File.separator + storageProperties.getCoursesPath();
    }

    public String getCourseVideosPath() {
        return storageProperties.getVideoPath() + File.separator + storageProperties.getCoursesPath();
    }
}
