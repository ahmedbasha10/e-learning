package com.logicerror.e_learning.courses.exceptions;

import com.logicerror.e_learning.exceptions.general.ResourceAlreadyExistsException;

public class CourseTitleAlreadyExistsException extends ResourceAlreadyExistsException {
    public CourseTitleAlreadyExistsException(String message) {
        super(message);
    }
}
