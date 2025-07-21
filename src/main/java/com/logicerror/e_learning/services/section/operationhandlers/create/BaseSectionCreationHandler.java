package com.logicerror.e_learning.services.section.operationhandlers.create;

import com.logicerror.e_learning.services.OperationHandler;

public abstract class BaseSectionCreationHandler implements OperationHandler<SectionCreationContext> {
    private OperationHandler<SectionCreationContext> nextHandler;

    @Override
    public void handle(SectionCreationContext sectionCreationContext) {
        // Process the request
        processRequest(sectionCreationContext);
        if (nextHandler != null) {
            nextHandler.handle(sectionCreationContext);
        }
    }

    @Override
    public void setNextHandler(OperationHandler<SectionCreationContext> nextHandler) {
        this.nextHandler = nextHandler;
    }

    protected abstract void processRequest(SectionCreationContext sectionCreationContext);
}


// authorize that the user is allowed to create a section (admin - teacher and owner of the course)
// check if the section title is unique in the course
// check if the section order is unique in the course
// create the section and save it to the database
// set the section to the course