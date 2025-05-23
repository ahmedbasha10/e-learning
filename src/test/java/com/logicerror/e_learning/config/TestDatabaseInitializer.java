package com.logicerror.e_learning.config;

import com.logicerror.e_learning.entities.user.Role;
import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.repositories.RoleRepository;
import com.logicerror.e_learning.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@TestConfiguration
public class TestDatabaseInitializer {

    @Bean
    public CommandLineRunner insertRolesForTests(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            List<String> roles = List.of("ROLE_ADMIN", "ROLE_TEACHER", "ROLE_STUDENT");
            for (String roleName : roles) {
                if (roleRepository.findByName(roleName).isEmpty()) {
                    Role role = new Role();
                    role.setName(roleName);
                    roleRepository.save(role);
                    System.out.println("Test role inserted: " + roleName);
                }
            }

            User student = new User();
            student.setUsername("student");
            student.setPassword(passwordEncoder.encode("password"));
            student.setEmail("student@localhost");
            student.setFirstName("Student");
            student.setLastName("Student");
            student.setCountry("Country");
            student.setCity("City");
            student.setState("State");
            student.setRole(roleRepository.findByName("ROLE_STUDENT").get());
            User savedUser = userRepository.save(student);
            System.out.println("savedUser = " + savedUser);
        };
    }
}
