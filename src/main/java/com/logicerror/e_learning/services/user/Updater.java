package com.logicerror.e_learning.services.user;

public interface Updater<T, U>{
    void update(T entity, U updateRequest);
}
