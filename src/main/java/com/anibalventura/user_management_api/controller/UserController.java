package com.anibalventura.user_management_api.controller;

import com.anibalventura.user_management_api.dto.RegisterDTO;
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
}