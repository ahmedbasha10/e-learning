package com.logicerror.e_learning.videos.services.models;

import org.springframework.core.io.Resource;


public record VideoFileInfo(Resource resource, long contentLength, String contentType) {
}
