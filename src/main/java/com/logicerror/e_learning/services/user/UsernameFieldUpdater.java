package com.logicerror.e_learning.services.user;

import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.exceptions.user.UserAlreadyExistsException;
import com.logicerror.e_learning.repositories.UserRepository;
import com.logicerror.e_learning.requests.user.UpdateUserRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UsernameFieldUpdater implements UserFieldUpdater {
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UsernameFieldUpdater.class);

    public UsernameFieldUpdater(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void updateField(User user, UpdateUserRequest updateUserRequest) {
        String newUsername = updateUserRequest.getUsername();
        if (newUsername != null && !newUsername.equals(user.getUsername())) {
            if(userRepository.existsByUsername(newUsername)) {
                logger.error("Username is already in use: {}", user.getUsername());
                throw new UserAlreadyExistsException("Username is already in use.");
            }
            user.setUsername(newUsername);
        }
    }
}
