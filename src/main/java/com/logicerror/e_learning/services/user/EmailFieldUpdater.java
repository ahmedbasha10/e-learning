package com.logicerror.e_learning.services.user;

import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.exceptions.user.UserAlreadyExistsException;
import com.logicerror.e_learning.repositories.UserRepository;
import com.logicerror.e_learning.requests.user.UpdateUserRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EmailFieldUpdater implements UserFieldUpdater {

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(EmailFieldUpdater.class);

    public EmailFieldUpdater(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public void updateField(User user, UpdateUserRequest updateUserRequest) {
        if (updateUserRequest.getEmail() != null && !updateUserRequest.getEmail().equals(user.getEmail())) {
            if(userRepository.existsByEmail(updateUserRequest.getEmail())) {
                logger.error("Email is already in use: {}", user.getEmail());
                throw new UserAlreadyExistsException("Email is already in use.");
            }
            user.setEmail(updateUserRequest.getEmail());
        }
    }
}
