package com.anibalventura.user_management_api.controllers;

import com.anibalventura.user_management_api.dtos.PaginatedResponseDTO;
import com.anibalventura.user_management_api.models.User;
import com.anibalventura.user_management_api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @GetMapping
  public ResponseEntity<PaginatedResponseDTO<User>> getAllUsers(
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "10") int size) {
    Pageable pageable = PageRequest.of(page, size);
    PaginatedResponseDTO<User> response = userService.getAllUsers(pageable);
    return ResponseEntity.ok(response);
  }
}