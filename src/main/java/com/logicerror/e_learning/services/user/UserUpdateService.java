package com.logicerror.e_learning.services.user;

import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.requests.user.UpdateUserRequest;
import com.logicerror.e_learning.services.Updater;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserUpdateService implements Updater<User, UpdateUserRequest> {
    private final List<UserFieldUpdater> fieldUpdaters;

    @Override
    public void update(User user, UpdateUserRequest request) {
        fieldUpdaters
                .forEach(fieldUpdater -> fieldUpdater.updateField(user, request));
    }
}
