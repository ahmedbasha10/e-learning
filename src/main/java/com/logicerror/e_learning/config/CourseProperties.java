package com.logicerror.e_learning.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.course")
@Data
public class CourseProperties {
    private String defaultThumbnail;
}
