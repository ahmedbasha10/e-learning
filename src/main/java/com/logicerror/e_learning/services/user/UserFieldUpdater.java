package com.logicerror.e_learning.services.user;

import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.requests.user.UpdateUserRequest;

public interface UserFieldUpdater {
    void updateField(User user, UpdateUserRequest updateUserRequest);
}
