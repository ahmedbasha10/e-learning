package com.logicerror.e_learning.sections.exceptions;

import com.logicerror.e_learning.exceptions.general.ResourceNotFoundException;

public class SectionNotFoundException extends ResourceNotFoundException {
    public SectionNotFoundException(String message) {
        super(message);
    }
}
