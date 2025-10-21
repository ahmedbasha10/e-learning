package com.logicerror.e_learning.videos.exceptions;

import com.logicerror.e_learning.exceptions.general.ResourceCreationFailedException;

public class VideoCreationFailedException extends ResourceCreationFailedException {
    public VideoCreationFailedException(String message) {
        super(message);
    }
}
