package com.logicerror.e_learning.exceptions.section;

import com.logicerror.e_learning.exceptions.general.ResourceNotFoundException;

public class SectionNotFoundException extends ResourceNotFoundException {
    public SectionNotFoundException(String message) {
        super(message);
    }
}
