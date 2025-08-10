package com.logicerror.e_learning.services;

public interface OperationHandler<T> {
    void handle(T context);
    void setNextHandler(OperationHandler<T> nextHandler);
}
