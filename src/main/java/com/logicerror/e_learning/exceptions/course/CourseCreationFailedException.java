package com.logicerror.e_learning.exceptions.course;

public class CourseCreationFailedException extends RuntimeException {
    public CourseCreationFailedException(String message) {
        super(message);
    }
}
