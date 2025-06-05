package com.logicerror.e_learning.services;

public interface FieldUpdater <T, U> {
    void updateField(T entity, U request);
}
