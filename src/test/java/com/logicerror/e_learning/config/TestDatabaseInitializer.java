package com.logicerror.e_learning.config;

import com.logicerror.e_learning.entities.user.Role;
import com.logicerror.e_learning.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.List;

@TestConfiguration
public class TestDatabaseInitializer {

    @Bean
    public CommandLineRunner insertRolesForTests(RoleRepository roleRepository) {
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
        };
    }
}
