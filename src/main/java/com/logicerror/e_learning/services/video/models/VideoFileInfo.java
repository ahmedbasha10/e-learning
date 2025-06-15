package com.logicerror.e_learning.services.video.models;

import org.springframework.core.io.Resource;


public record VideoFileInfo(Resource resource, long contentLength, String contentType) {
}
