package com.logicerror.e_learning.services;

public interface OperationHandler<T> {
    void handle(T courseCreationContext);
    OperationHandler<T> setNextHandler(OperationHandler<T> nextHandler);
}
