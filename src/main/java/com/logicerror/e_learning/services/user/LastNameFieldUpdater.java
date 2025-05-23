package com.logicerror.e_learning.services.user;

import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.requests.user.UpdateUserRequest;
import org.springframework.stereotype.Component;

@Component
public class LastNameFieldUpdater implements UserFieldUpdater {

    @Override
    public void updateField(User user, UpdateUserRequest updateUserRequest) {
        if (updateUserRequest.getLastName() != null) {
            user.setLastName(updateUserRequest.getLastName());
        }
    }
}
