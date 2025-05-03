package com.logicerror.e_learning.exceptions.course;

import com.logicerror.e_learning.exceptions.general.ResourceNotFoundException;

public class CourseNotFoundException extends ResourceNotFoundException {
    public CourseNotFoundException(String message) {
        super(message);
    }
}
