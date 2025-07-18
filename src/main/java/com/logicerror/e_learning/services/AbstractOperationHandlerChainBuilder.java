package com.logicerror.e_learning.services;

import java.util.List;

public abstract class AbstractOperationHandlerChainBuilder<T> {

    public OperationHandler<T> build() {
        List<OperationHandler<T>> operationHandlers = getOperationHandlers();
        if (operationHandlers.isEmpty()) {
            throw new IllegalStateException("No operation handlers provided for chain building.");
        }

        for(int i = 0; i < operationHandlers.size() - 1; i++) {
            operationHandlers.get(i).setNextHandler(operationHandlers.get(i + 1));
        }

        return operationHandlers.getFirst();
    }

    protected abstract List<OperationHandler<T>> getOperationHandlers();
}
