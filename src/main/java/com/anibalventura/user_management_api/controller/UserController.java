package com.anibalventura.user_management_api.controller;

import com.anibalventura.user_management_api.dto.UserDTO;
import com.anibalventura.user_management_api.model.User;
import com.anibalventura.user_management_api.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userDTO) {
    try {
      User user = userService.registerUser(userDTO);
      return ResponseEntity.status(HttpStatus.CREATED).body(user);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"mensaje\": \"" + e.getMessage() + "\"}");
    }
  }
}