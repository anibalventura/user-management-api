package com.anibalventura.user_management_api.controllers;

import com.anibalventura.user_management_api.dtos.LoginDTO;
import com.anibalventura.user_management_api.dtos.RegisterDTO;
import com.anibalventura.user_management_api.models.User;
import com.anibalventura.user_management_api.services.AuthService;
import com.anibalventura.user_management_api.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user authentication")
public class AuthController {
    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Register a new user with the provided details",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User registered successfully",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(value = """
                                            {
                                                  "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                                                  "name": "string",
                                                  "email": "string",
                                                  "password": "string",
                                                  "created": "2025-03-26T15:34:04.547Z",
                                                  "modified": "2025-03-26T15:34:04.547Z",
                                                  "lastLogin": "2025-03-26T15:34:04.547Z",
                                                  "token": "string",
                                                  "phones": [
                                                    {
                                                      "id": 0,
                                                      "number": "string",
                                                      "cityCode": "string",
                                                      "countryCode": "string"
                                                    }
                                                  ],
                                                  "active": true
                                                }
                                            """))),
                    @ApiResponse(responseCode = "400", description = "Invalid input",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(value = "{\"mensaje\": \"Invalid input\"}")))
            })
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterDTO registerDTO) {
        try {
            User user = authService.registerUser(registerDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("mensaje", e.getMessage()));
        }
    }

    @PostMapping("/login")
    @Operation(summary = "Login a user", description = "Authenticate a user and return a JWT token",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User authenticated successfully",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(value = "{\"token\": \"jwt-token\"}"))),
                    @ApiResponse(responseCode = "403", description = "Invalid credentials",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(value = "{\"mensaje\": \"Invalid credentials\"}")))
            })
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO) {
        try {
            User user = authService.loginUser(loginDTO);
            String token = jwtUtil.generateToken(user.getEmail());
            return ResponseEntity.ok().body(Collections.singletonMap("token", token));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Collections.singletonMap("mensaje", e.getMessage()));
        }
    }
}