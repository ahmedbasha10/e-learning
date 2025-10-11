package com.logicerror.e_learning.sections.exceptions;

import com.logicerror.e_learning.exceptions.general.ResourceAlreadyExistsException;

public class SectionAlreadyExistsException extends ResourceAlreadyExistsException {
    public SectionAlreadyExistsException(String message) {
        super(message);
    }
}
