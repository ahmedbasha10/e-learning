package com.logicerror.e_learning.security.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.logicerror.e_learning.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Component("userSecurityUtils")
@RequiredArgsConstructor
public class UserSecurityUtils {
    private final UserRepository userRepository;


    public boolean isAdmin(String role) {
        return role.equals("ADMIN");
    }

    public boolean isTeacher(String role) {
        return role.equals("TEACHER");
    }

    public boolean isStudent(String role) {
        return role.equals("STUDENT");
    }

    public boolean isOwnerById(Long userId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return userRepository.findByUsername(username)
                .map(user -> user.getId().equals(userId))
                .orElse(false);
    }

    public boolean isOwnerByEmail(String email){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return userRepository.findByUsername(username)
               .map(user -> user.getEmail().equals(email))
               .orElse(false);
    }

    public boolean isOwnerByUsername(String username){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        return currentUsername.equals(username);
    }
}
