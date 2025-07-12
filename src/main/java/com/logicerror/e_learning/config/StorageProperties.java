package com.logicerror.e_learning.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "video-storage")
@Data
public class StorageProperties {

    private final String basePath = "storage";
    private final String videoPath = "videos";
    private final String imagePath = "images";
    private final String coursesPath = "courses";

    private final long maxFileSize = 524288000; // 500MB

}
