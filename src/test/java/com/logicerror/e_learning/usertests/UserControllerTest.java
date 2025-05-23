package com.logicerror.e_learning.usertests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logicerror.e_learning.config.TestDatabaseInitializer;
import com.logicerror.e_learning.dto.RoleDto;
import com.logicerror.e_learning.requests.user.CreateUserRequest;
import com.logicerror.e_learning.requests.user.UpdateUserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(TestDatabaseInitializer.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;



    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void registerUser_shouldReturn201AndUserData() throws Exception {
        long time = System.currentTimeMillis();
        CreateUserRequest userRequest = new CreateUserRequest(
                "Ahmed" + time,
                "ahmed" + time + "@gmail.com",
                "Password_123",
                "Ahmed",
                "Basha",
                "Egypt",
                "Cairo",
                "Cairo",
                new RoleDto("ROLE_STUDENT")
        );

        ResultActions resultActions = mockMvc.perform(post("/api/v1/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)));

        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("User registered successfully"))
                .andExpect(jsonPath("$.data.username").value("Ahmed" + time))
                .andExpect(jsonPath("$.data.email").value("ahmed" + time + "@gmail.com"))
                .andExpect(jsonPath("$.data.firstName").value("Ahmed"))
                .andExpect(jsonPath("$.data.lastName").value("Basha"))
                .andExpect(jsonPath("$.data.country").value("Egypt"))
                .andExpect(jsonPath("$.data.city").value("Cairo"))
                .andExpect(jsonPath("$.data.state").value("Cairo"));

        // GET THE CREATED USER ID
        String response = resultActions.andReturn().getResponse().getContentAsString();
        String userId = objectMapper.readTree(response).get("data").get("id").asText();
        System.out.println("Created User ID: " + userId);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void registerUser_shouldReturn400WhenUsernameExists() throws Exception {
        long time = System.currentTimeMillis();
        CreateUserRequest userRequest = new CreateUserRequest(
                "Ahmed" + time,
                "ahmed" + time + "@gmail.com",
                "Password_123",
                "Ahmed",
                "Basha",
                "Egypt",
                "Cairo",
                "Cairo",
                new RoleDto("ROLE_STUDENT")
        );

        // First registration
        mockMvc.perform(post("/api/v1/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated());

        // Second registration with the same username
        mockMvc.perform(post("/api/v1/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Email or username is already in use."));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void registerUser_shouldReturn409WhenEmailExists() throws Exception {
        long time = System.currentTimeMillis();
        CreateUserRequest userRequest = new CreateUserRequest(
                "Ahmed" + time,
                "ahmed" + time + "@gmail.com",
                "Password_123",
                "Ahmed",
                "Basha",
                "Egypt",
                "Cairo",
                "Cairo",
                new RoleDto("ROLE_STUDENT")
        );

        // First registration
        mockMvc.perform(post("/api/v1/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated());

        // Second registration with the same email
        mockMvc.perform(post("/api/v1/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Email or username is already in use."));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void registerUser_shouldReturn400WhenRoleNotFound() throws Exception {
        long time = System.currentTimeMillis();
        CreateUserRequest userRequest = new CreateUserRequest(
                "Ahmed" + time,
                "ahmed" + time + "@gmail.com",
                "Password_123",
                "Ahmed",
                "Basha",
                "Egypt",
                "Cairo",
                "Cairo",
                new RoleDto("ROLE_NON_EXISTENT")
        );

        mockMvc.perform(post("/api/v1/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Role not found: " + userRequest.getRole().getName()));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void registerUser_shouldReturn400WhenInvalidInput() throws Exception {
        CreateUserRequest userRequest = new CreateUserRequest(
                null,
                "invalid-email",
                "short",
                null,
                null,
                null,
                null,
                null,
                new RoleDto("ROLE_STUDENT")
        );

        mockMvc.perform(post("/api/v1/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid input data"))
                .andExpect(jsonPath("$.data.username").value("Username is required"))
                .andExpect(jsonPath("$.data.email").value("Email should be valid"))
                .andExpect(jsonPath("$.data.password").value("Password must contain at least one uppercase letter, one lowercase letter, one number, one special character, and be at least 8 characters long and max 20 characters long"))
                .andExpect(jsonPath("$.data.firstName").value("First name is required"))
                .andExpect(jsonPath("$.data.lastName").value("Last name is required"))
                .andExpect(jsonPath("$.data.country").value("Country is required"))
                .andExpect(jsonPath("$.data.city").value("City is required"));

    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void registerUser_shouldReturn400WhenInvalidEmail() throws Exception {
        long time = System.currentTimeMillis();
        CreateUserRequest userRequest = new CreateUserRequest(
                "Ahmed" + time,
                "invalid-email",
                "Password_123",
                "Ahmed",
                "Basha",
                "Egypt",
                "Cairo",
                "Cairo",
                new RoleDto("ROLE_STUDENT")
        );

        mockMvc.perform(post("/api/v1/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid input data"))
                .andExpect(jsonPath("$.data.email").value("Email should be valid"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void registerUser_shouldReturn400WhenInvalidPassword() throws Exception {
        long time = System.currentTimeMillis();
        CreateUserRequest userRequest = new CreateUserRequest(
                "Ahmed" + time,
                "ahmed" + time + "@gmail.com",
                "short",
                "Ahmed",
                "Basha",
                "Egypt",
                "Cairo",
                "Cairo",
                new RoleDto("ROLE_STUDENT")
        );

        mockMvc.perform(post("/api/v1/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid input data"))
                .andExpect(jsonPath("$.data.password").value("Password must contain at least one uppercase letter, one lowercase letter, one number, one special character, and be at least 8 characters long and max 20 characters long"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void registerUser_shouldReturn400WhenInvalidUsername() throws Exception {
        long time = System.currentTimeMillis();
        CreateUserRequest userRequest = new CreateUserRequest(
                "",
                "ahmed" + time + "@gmail.com",
                "Password_123",
                "Ahmed",
                "Basha",
                "Egypt",
                "Cairo",
                "Cairo",
                new RoleDto("ROLE_STUDENT")
        );

        mockMvc.perform(post("/api/v1/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid input data"))
                .andExpect(jsonPath("$.data.username").value("Username must be between 4 and 20 characters"));
    }

    // GET USER BY USERNAME
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getUserByUsername_shouldReturn200AndUserData() throws Exception {
        long time = System.currentTimeMillis();
        CreateUserRequest userRequest = new CreateUserRequest(
                "Ahmed" + time,
                "ahmed" + time + "@gmail.com",
                "Password_123",
                "Ahmed",
                "Basha",
                "Egypt",
                "Cairo",
                "Cairo",
                new RoleDto("ROLE_STUDENT")
        );

        // First registration
        mockMvc.perform(post("/api/v1/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated());

        // Get user by username
        mockMvc.perform(get("/api/v1/users/user/username/" + userRequest.getUsername()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User retrieved successfully"))
                .andExpect(jsonPath("$.data.username").value("Ahmed" + time))
                .andExpect(jsonPath("$.data.email").value("ahmed" + time + "@gmail.com"))
                .andExpect(jsonPath("$.data.firstName").value("Ahmed"))
                .andExpect(jsonPath("$.data.lastName").value("Basha"))
                .andExpect(jsonPath("$.data.country").value("Egypt"))
                .andExpect(jsonPath("$.data.city").value("Cairo"))
                .andExpect(jsonPath("$.data.state").value("Cairo"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getUserByUsername_shouldReturn404WhenUserNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/users/user/username/nonexistentuser"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found with username: nonexistentuser"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getUserByEmail_shouldReturn200AndUserData() throws Exception {
        long time = System.currentTimeMillis();
        CreateUserRequest userRequest = new CreateUserRequest(
                "Ahmed" + time,
                "ahmed" + time + "@gmail.com",
                "Password_123",
                "Ahmed",
                "Basha",
                "Egypt",
                "Cairo",
                "Cairo",
                new RoleDto("ROLE_STUDENT")
        );

        // First registration
        mockMvc.perform(post("/api/v1/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated());

        // Get user by email
        mockMvc.perform(get("/api/v1/users/user/email/" + userRequest.getEmail()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User retrieved successfully"))
                .andExpect(jsonPath("$.data.username").value("Ahmed" + time))
                .andExpect(jsonPath("$.data.email").value("ahmed" + time + "@gmail.com"))
                .andExpect(jsonPath("$.data.firstName").value("Ahmed"))
                .andExpect(jsonPath("$.data.lastName").value("Basha"))
                .andExpect(jsonPath("$.data.country").value("Egypt"))
                .andExpect(jsonPath("$.data.city").value("Cairo"))
                .andExpect(jsonPath("$.data.state").value("Cairo"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getUserByEmail_shouldReturn404WhenUserNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/users/user/email/nonexistentemail"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found with email: nonexistentemail"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getUserById_shouldReturn200AndUserData() throws Exception {
        long time = System.currentTimeMillis();
        CreateUserRequest userRequest = new CreateUserRequest(
                "Ahmed" + time,
                "ahmed" + time + "@gmail.com",
                "Password_123",
                "Ahmed",
                "Basha",
                "Egypt",
                "Cairo",
                "Cairo",
                new RoleDto("ROLE_STUDENT")
        );

        // First registration
        String response = mockMvc.perform(post("/api/v1/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Get the created user ID
        String userId = objectMapper.readTree(response).get("data").get("id").asText();

        // Get user by ID
        mockMvc.perform(get("/api/v1/users/user/id/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User retrieved successfully"))
                .andExpect(jsonPath("$.data.username").value("Ahmed" + time))
                .andExpect(jsonPath("$.data.email").value("ahmed" + time + "@gmail.com"))
                .andExpect(jsonPath("$.data.firstName").value("Ahmed"))
                .andExpect(jsonPath("$.data.lastName").value("Basha"))
                .andExpect(jsonPath("$.data.country").value("Egypt"))
                .andExpect(jsonPath("$.data.city").value("Cairo"))
                .andExpect(jsonPath("$.data.state").value("Cairo"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getUserById_shouldReturn404WhenUserNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/users/user/id/999999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found with ID: 999999"));
    }

    // Delete user
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deleteUser_shouldReturn200() throws Exception {
        long time = System.currentTimeMillis();
        CreateUserRequest userRequest = new CreateUserRequest(
                "Ahmed" + time,
                "ahmed" + time + "@gmail.com",
                "Password_123",
                "Ahmed",
                "Basha",
                "Egypt",
                "Cairo",
                "Cairo",
                new RoleDto("ROLE_STUDENT")
        );

        // First registration
        String response = mockMvc.perform(post("/api/v1/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Get the created user ID
        String userId = objectMapper.readTree(response).get("data").get("id").asText();

        // Delete user
        mockMvc.perform(delete("/api/v1/users/user/delete/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User deleted successfully"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deleteUser_shouldReturn404WhenUserNotFound() throws Exception {
        mockMvc.perform(delete("/api/v1/users/user/delete/999999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found with ID: 999999"));
    }

    // Update user
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void updateUser_shouldReturn200() throws Exception {
        long time = System.currentTimeMillis();
        CreateUserRequest userRequest = new CreateUserRequest(
                "Ahmed" + time,
                "ahmed" + time + "@gmail.com",
                "Password_123",
                "Ahmed",
                "Basha",
                "Egypt",
                "Cairo",
                "Cairo",
                new RoleDto("ROLE_STUDENT")
        );

        // First registration
        String response = mockMvc.perform(post("/api/v1/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Get the created user ID
        String userId = objectMapper.readTree(response).get("data").get("id").asText();

        // Update user
        UpdateUserRequest updateUserRequest = new UpdateUserRequest(
                "Ahmed Updated",
                "ahmed" + time + "Updated" + "@gmail.com",
                "Password_123w34",
                "Ahmed",
                "Bashaasdasd"
        );

        mockMvc.perform(patch("/api/v1/users/user/update/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUserRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User updated successfully"))
                .andExpect(jsonPath("$.data.username").value(updateUserRequest.getUsername()))
                .andExpect(jsonPath("$.data.email").value(updateUserRequest.getEmail()))
                .andExpect(jsonPath("$.data.firstName").value(updateUserRequest.getFirstName()))
                .andExpect(jsonPath("$.data.lastName").value(updateUserRequest.getLastName()));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void updateUser_shouldReturn404WhenUserNotFound() throws Exception {
        UpdateUserRequest updateUserRequest = new UpdateUserRequest(
                "Ahmed Updated",
                "ahmed Updated" + "@gmail.com",
                "Password_123w34",
                "Ahmed",
                "Bashaasdasd"
        );

        mockMvc.perform(patch("/api/v1/users/user/update/999999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUserRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found with ID: 999999"));
    }


    /// Security Tests
    @Test
    @WithMockUser(username = "user", roles = {"STUDENT"})
    void getAllUsers_notAuthorized() throws Exception {
        mockMvc.perform(get("/api/v1/admin/users/all"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value("Access Denied"))
                .andExpect(jsonPath("$.path").value("/api/v1/admin/users/all"))
                .andExpect(jsonPath("$.error").value("Forbidden"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getAllUsers_authorized() throws Exception {
        mockMvc.perform(get("/api/v1/admin/users/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Users retrieved successfully"));
    }

    @Test
    @WithMockUser(username = "ahmed", roles = {"STUDENT"})
    void getUserByUsername_notAuthorized_notSameUser() throws Exception {
        mockMvc.perform(get("/api/v1/users/user/username/student"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value("Access Denied"));
    }

    @Test
    @WithUserDetails("student")
    void getUserByUsername_authorized_sameUser() throws Exception {
        mockMvc.perform(get("/api/v1/users/user/username/student"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User retrieved successfully"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getUserByUsername_authorized_admin() throws Exception {
        mockMvc.perform(get("/api/v1/users/user/username/student"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User retrieved successfully"));
    }
}
