package com.logicerror.e_learning.courses.exceptions;

import com.logicerror.e_learning.exceptions.general.ResourceCreationFailedException;

public class CourseCreationFailedException extends ResourceCreationFailedException {
    public CourseCreationFailedException(String message) {
        super(message);
    }
}
