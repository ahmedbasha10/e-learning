package com.logicerror.e_learning.usertests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logicerror.e_learning.dto.RoleDto;
import com.logicerror.e_learning.requests.user.CreateUserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void registerUser_shouldReturn201AndUserData() throws Exception {
        long time = System.currentTimeMillis();
        CreateUserRequest userRequest = new CreateUserRequest(
                "Ahmed" + time,
                "ahmed" + time + "@gmail.com",
                "password123",
                "Ahmed",
                "Basha",
                "Egypt",
                "Cairo",
                "Cairo",
                new RoleDto("role_student")
        );

        mockMvc.perform(post("/api/v1/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("User registered successfully"))
                .andExpect(jsonPath("$.data.username").value("Ahmed" + time))
                .andExpect(jsonPath("$.data.email").value("ahmed" + time + "@gmail.com"))
                .andExpect(jsonPath("$.data.firstName").value("Ahmed"))
                .andExpect(jsonPath("$.data.lastName").value("Basha"))
                .andExpect(jsonPath("$.data.country").value("Egypt"))
                .andExpect(jsonPath("$.data.city").value("Cairo"))
                .andExpect(jsonPath("$.data.state").value("Cairo"));
    }
}
