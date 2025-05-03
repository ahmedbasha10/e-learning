package com.logicerror.e_learning.exceptions.course;

import com.logicerror.e_learning.exceptions.general.ResourceCreationFailedException;

public class CourseCreationFailedException extends ResourceCreationFailedException {
    public CourseCreationFailedException(String message) {
        super(message);
    }
}
