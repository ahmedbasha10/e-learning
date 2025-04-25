package com.logicerror.e_learning.services.user;

import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.requests.UpdateUserRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class PasswordFieldUpdater implements UserFieldUpdater {

    private final PasswordEncoder passwordEncoder;

    @Override
    public void updateField(User user, UpdateUserRequest updateUserRequest) {
        if (updateUserRequest.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(updateUserRequest.getPassword()));
        }
    }
}
