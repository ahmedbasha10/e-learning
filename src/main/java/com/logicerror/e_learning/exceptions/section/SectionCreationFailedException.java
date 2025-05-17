package com.logicerror.e_learning.exceptions.section;

import com.logicerror.e_learning.exceptions.general.ResourceCreationFailedException;

public class SectionCreationFailedException extends ResourceCreationFailedException {
    public SectionCreationFailedException(String message) {
        super(message);
    }
}
