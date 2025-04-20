package com.logicerror.e_learning.repositories;

import com.logicerror.e_learning.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmailOrUsername(String email, String username);
}
