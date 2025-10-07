package com.logicerror.e_learning.config;

import com.logicerror.e_learning.courses.constants.CourseLevel;
import com.logicerror.e_learning.courses.entities.Course;
import com.logicerror.e_learning.entities.teacher.TeacherCourses;
import com.logicerror.e_learning.entities.teacher.TeacherCoursesKey;
import com.logicerror.e_learning.entities.user.Role;
import com.logicerror.e_learning.entities.user.User;
import com.logicerror.e_learning.courses.repositories.CourseRepository;
import com.logicerror.e_learning.repositories.RoleRepository;
import com.logicerror.e_learning.repositories.TeacherCoursesRepository;
import com.logicerror.e_learning.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@TestConfiguration
public class TestDatabaseInitializer {

    @Bean
    public CommandLineRunner insertRolesForTests(RoleRepository roleRepository, UserRepository userRepository, CourseRepository courseRepository, TeacherCoursesRepository teacherCoursesRepository, PasswordEncoder passwordEncoder) {
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

            User teacher = new User();
            teacher.setUsername("teacher");
            teacher.setPassword(passwordEncoder.encode("password"));
            teacher.setEmail("teacher@localhost");
            teacher.setFirstName("teacher");
            teacher.setLastName("teacher");
            teacher.setCountry("Country");
            teacher.setCity("City");
            teacher.setState("State");
            teacher.setRole(roleRepository.findByName("ROLE_TEACHER").get());
            User savedTeacher = userRepository.save(teacher);
            System.out.println("saved teacher = " + savedUser);


            Course course = new Course();
            course.setTitle("Test Course");
            course.setDescription("This is a test course for unit testing.");
            course.setCategory("Test Category");
            course.setLevel(CourseLevel.BEGINNER);
            course.setPrice(50);
            course.setDuration(120);
            course.setImageUrl("http://example.com/test-course.jpg");

            Course savedCourse = courseRepository.save(course);

            teacherCoursesRepository.save(TeacherCourses.builder()
                    .course(savedCourse)
                    .user(savedTeacher)
                    .id(TeacherCoursesKey.builder()
                            .courseId(savedCourse.getId())
                            .userId(savedTeacher.getId())
                            .build())
                    .build());
        };
    }
}
