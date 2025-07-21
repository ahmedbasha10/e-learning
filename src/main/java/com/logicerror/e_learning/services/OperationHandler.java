package com.logicerror.e_learning.services;

public interface OperationHandler<T> {
    void handle(T courseCreationContext);
    void setNextHandler(OperationHandler<T> nextHandler);
}
