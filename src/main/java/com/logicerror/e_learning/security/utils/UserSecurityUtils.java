package com.logicerror.e_learning.security.utils;

import com.logicerror.e_learning.constants.RoleConstants;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.logicerror.e_learning.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Component("userSecurityUtils")
@RequiredArgsConstructor
public class UserSecurityUtils {
    private final UserRepository userRepository;

    public boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(RoleConstants.ROLE_ADMIN_WITH_PREFIX));
    }

    public boolean canAccessUserById(Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if admin
        if (authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(RoleConstants.ROLE_ADMIN_WITH_PREFIX))) {
            return true;
        }

        // Check if owner
        return isOwnerById(userId);
    }

    public boolean canAccessUserByUsername(String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if admin
        if (authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(RoleConstants.ROLE_ADMIN_WITH_PREFIX))) {
            return true;
        }

        // Check if owner
        return isOwnerByUsername(username);
    }

    public boolean canAccessUserByEmail(String email) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if admin
        if (authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(RoleConstants.ROLE_ADMIN_WITH_PREFIX))) {
            return true;
        }

        // Check if owner
        return isOwnerByEmail(email);
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
