package com.logicerror.e_learning.videos.exceptions;

import com.logicerror.e_learning.exceptions.general.ResourceAlreadyExistsException;

public class VideoTitleAlreadyExistsException extends ResourceAlreadyExistsException {
    public VideoTitleAlreadyExistsException(String message) {
        super(message);
    }
}
