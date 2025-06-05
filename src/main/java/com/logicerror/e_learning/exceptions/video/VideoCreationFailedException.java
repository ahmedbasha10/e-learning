package com.logicerror.e_learning.exceptions.video;

import com.logicerror.e_learning.exceptions.general.ResourceCreationFailedException;

public class VideoCreationFailedException extends ResourceCreationFailedException {
    public VideoCreationFailedException(String message) {
        super(message);
    }
}
