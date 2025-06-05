package com.logicerror.e_learning.exceptions.video;

import com.logicerror.e_learning.exceptions.general.ResourceAlreadyExistsException;

public class VideoTitleAlreadyExistsException extends ResourceAlreadyExistsException {
    public VideoTitleAlreadyExistsException(String message) {
        super(message);
    }
}
