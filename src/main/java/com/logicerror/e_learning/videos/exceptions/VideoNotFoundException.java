package com.logicerror.e_learning.videos.exceptions;

import com.logicerror.e_learning.exceptions.general.ResourceNotFoundException;

public class VideoNotFoundException extends ResourceNotFoundException {
    public VideoNotFoundException(String message) {
        super(message);
    }
}
