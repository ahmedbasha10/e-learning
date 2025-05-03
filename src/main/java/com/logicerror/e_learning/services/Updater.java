package com.logicerror.e_learning.services;

public interface Updater<T, U>{
    void update(T entity, U updateRequest);
}
