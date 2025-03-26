package com.anibalventura.user_management_api.controllers;

import com.anibalventura.user_management_api.dtos.LoginDTO;
import com.anibalventura.user_management_api.dtos.RegisterDTO;
import com.anibalventura.user_management_api.models.User;
import com.anibalventura.user_management_api.services.UserService;
import com.anibalventura.user_management_api.utils.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterDTO registerDTO) {
        try {
            User user = userService.registerUser(registerDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("mensaje", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO) {
        try {
            User user = userService.authenticateUser(loginDTO);
            String token = jwtUtil.generateToken(user.getEmail());
            return ResponseEntity.ok().body(Collections.singletonMap("token", token));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Collections.singletonMap("mensaje", e.getMessage()));
        }
    }
}
