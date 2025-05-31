package com.logicerror.e_learning.coursetests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logicerror.e_learning.config.TestDatabaseInitializer;
import com.logicerror.e_learning.services.OperationHandler;
import com.logicerror.e_learning.services.course.operationhandlers.creation.CourseCreationChainBuilder;
import com.logicerror.e_learning.services.course.operationhandlers.creation.CourseCreationContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(TestDatabaseInitializer.class)
public class CourseControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    private CourseCreationChainBuilder courseCreationChainBuilder;

    private OperationHandler<CourseCreationContext> courseOperationHandler;

    // test get methods (should be accessible to all users)

        // test get all courses method
    @Test
    @WithMockUser(username = "student", roles = {"STUDENT"})
    public void testGetAllCourses() throws Exception {
        mockMvc.perform(get("/api/v1/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Courses fetched successfully"));
    }

        // test get course by id method
    @Test
    @WithMockUser(username = "student", roles = {"STUDENT"})
    public void testGetCourseById() throws Exception {
        mockMvc.perform(get("/api/v1/courses/1")) // Assuming course with ID 1 exists
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Course fetched successfully"));
    }
        // test get course by wrong id (should return 404 Not Found)
    @Test
    @WithMockUser(username = "student", roles = {"STUDENT"})
    public void testGetCourseByWrongId() throws Exception {
        mockMvc.perform(get("/api/v1/courses/999")) // Assuming course with ID 999 does not exist
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Course not found with id: 999"));
    }

        // test get course by title method
    @Test
    @WithMockUser(username = "student", roles = {"STUDENT"})
    public void testGetCourseByTitle() throws Exception {
        mockMvc.perform(get("/api/v1/courses/title/Test Course")) // Assuming course with this title exists
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Course fetched successfully"));
    }
        // test get course by wrong title (should return 404 Not Found)
    @Test
    @WithMockUser(username = "student", roles = {"STUDENT"})
    public void testGetCourseByWrongTitle() throws Exception {
        mockMvc.perform(get("/api/v1/courses/title/Invalid Title")) // Assuming course with this title does not exist
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Course not found with title: Invalid Title"));
    }

        // test get courses by category method
    @Test
    @WithMockUser(username = "student", roles = {"STUDENT"})
    public void testGetCoursesByCategory() throws Exception {
        mockMvc.perform(get("/api/v1/courses/category/Test Category")) // Assuming courses in this category exist
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Courses fetched successfully"));
    }
        // test get courses by wrong category (should return 200 Empty List)
    @Test
    @WithMockUser(username = "student", roles = {"STUDENT"})
    public void testGetCoursesByWrongCategory() throws Exception {
        mockMvc.perform(get("/api/v1/courses/category/Invalid Category")) // Assuming no courses in this category exist
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content").isEmpty())
                .andExpect(jsonPath("$.message").value("Courses fetched successfully"));
    }

        // test get courses by level method
    @Test
    @WithMockUser(username = "student", roles = {"STUDENT"})
    public void testGetCoursesByLevel() throws Exception {
        mockMvc.perform(get("/api/v1/courses/level/Beginner")) // Assuming courses with this level exist
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Courses fetched successfully"));
    }

        // test get courses by wrong level (should return 200 Empty List)
    @Test
    @WithMockUser(username = "student", roles = {"STUDENT"})
    public void testGetCoursesByWrongLevel() throws Exception {
        mockMvc.perform(get("/api/v1/courses/level/Invalid Level")) // Assuming no courses with this level exist
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content").isEmpty())
                .andExpect(jsonPath("$.message").value("Courses fetched successfully"));
    }


    // test create course method

        // test create course with admin user (should return 201)

        // test create course with teacher user (should return 201)

        // test create course with unauthorized user (should return 403 Forbidden) (student)

        // test create course with invalid data (should return 400 Bad Request)

        // test create course with duplicate title (should return 400 Bad Request)


    // test update course method

        // test update course with admin user (should return 200 OK)

        // test update course with teacher user (should return 200 OK)

        // test update course with unauthorized user (should return 403 Forbidden) (student)

        // test update course with non-owner user (should return 403 Forbidden)

        // test update course with invalid data (should return 400 Bad Request)

        // test update course with non-existing course id (should return 404 Not Found)

        // test update course with duplicate title (should return 409 Bad Request)

    // test delete course method
}
