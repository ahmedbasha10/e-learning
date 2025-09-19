package com.logicerror.e_learning.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration properties for course-related settings
 */
@Configuration
@ConfigurationProperties(prefix = "course")
@Data
public class CourseConfiguration {
    
    private Thumbnail thumbnail = new Thumbnail();
    private Validation validation = new Validation();
    private Pagination pagination = new Pagination();
    
    @Data
    public static class Thumbnail {
        private String defaultImage = "default-course-thumbnail.jpg";
        private String uploadPath = "uploads/courses/thumbnails";
        private long maxSizeBytes = 5 * 1024 * 1024; // 5MB
        private String[] allowedTypes = {"image/jpeg", "image/png", "image/gif"};
    }
    
    @Data
    public static class Validation {
        private int titleMinLength = 2;
        private int titleMaxLength = 100;
        private int descriptionMinLength = 10;
        private int descriptionMaxLength = 10000;
        private int categoryMaxLength = 50;
        private int priceMin = 0;
        private int priceMax = 999999;
    }
    
    @Data
    public static class Pagination {
        private int defaultPageSize = 20;
        private int maxPageSize = 100;
        private String defaultSort = "createdAt";
        private String defaultDirection = "desc";
    }
}

