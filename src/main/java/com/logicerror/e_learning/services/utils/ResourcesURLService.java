package com.logicerror.e_learning.services.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;

@Service
public class ResourcesURLService {

    @Value("${api.base-host}")
    private String DEFAULT_BASE_URL;

    public String buildResourceURL(String resourcePath) {
        if(resourcePath == null || resourcePath.isBlank()) {
            throw new IllegalArgumentException("Resource path must not be null or empty");
        }

        if(!resourcePath.startsWith(File.separator)) {
            resourcePath = File.separator + resourcePath;
        }

        resourcePath = resourcePath.replace("\\", "/");

        return DEFAULT_BASE_URL + resourcePath;
    }

}
