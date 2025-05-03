package com.logicerror.e_learning.exceptions.course;

public class CourseTitleAlreadyExistsException extends RuntimeException {
    public CourseTitleAlreadyExistsException(String message) {
        super(message);
    }
}
