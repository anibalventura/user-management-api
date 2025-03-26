package com.anibalventura.user_management_api.controllers;

import com.anibalventura.user_management_api.dtos.LoginDTO;
import com.anibalventura.user_management_api.dtos.RegisterDTO;
import com.anibalventura.user_management_api.models.User;
import com.anibalventura.user_management_api.services.AuthService;
import com.anibalventura.user_management_api.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @MockBean
    private JwtUtil jwtUtil;

    @Test
    void registerUser() throws Exception {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setName("name");
        user.setEmail("email@example.com");
        user.setPassword("password");

        Mockito.when(authService.registerUser(Mockito.any(RegisterDTO.class))).thenReturn(user);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"name\", \"email\": \"email@example.com\", \"password\": \"password\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("name")))
                .andExpect(jsonPath("$.email", is("email@example.com")));
    }

    @Test
    void login() throws Exception {
        User user = new User();
        user.setEmail("email@example.com");

        Mockito.when(authService.loginUser(Mockito.any(LoginDTO.class))).thenReturn(user);
        Mockito.when(jwtUtil.generateToken(user.getEmail())).thenReturn("jwt-token");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"email@example.com\", \"password\": \"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", is("jwt-token")));
    }
}