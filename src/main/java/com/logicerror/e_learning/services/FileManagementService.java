package com.logicerror.e_learning.services;

import com.logicerror.e_learning.config.StorageProperties;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;


@Service
public class FileManagementService {
    private final Path rootLocation;

    public FileManagementService(StorageProperties storageProperties) {
        this.rootLocation = Path.of(storageProperties.getBasePath());
    }

    public String uploadFile(InputStream inputStream, String filePath) throws IOException {
        if (inputStream == null || filePath == null || filePath.isBlank()) {
            throw new IllegalArgumentException("Input stream and file name must not be null or empty");
        }

        Path destinationFile = rootLocation.resolve(filePath).normalize().toAbsolutePath();

        if (!destinationFile.startsWith(rootLocation.toAbsolutePath())) {
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
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file: " + url, e);
        }
    }
}
