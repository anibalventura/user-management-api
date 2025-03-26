package com.anibalventura.user_management_api.controllers;

import com.anibalventura.user_management_api.dtos.PaginatedResponseDTO;
import com.anibalventura.user_management_api.models.User;
import com.anibalventura.user_management_api.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "User Management System")
@SecurityRequirement(name = "bearerAuth")
public class UserController {
  private final UserService userService;

  @GetMapping
  @Operation(summary = "Get all users with pagination", description = "Retrieve all users with pagination details")
  @ApiResponse(responseCode = "400", description = "Invalid input",
          content = @Content(mediaType = "application/json",
                  examples = @ExampleObject(value = "{\"mensaje\": \"Invalid input\"}")))
  public ResponseEntity<PaginatedResponseDTO<User>> getAllUsers(
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "10") int size) {
    Pageable pageable = PageRequest.of(page, size);
    PaginatedResponseDTO<User> response = userService.getAllUsers(pageable);
    return ResponseEntity.ok(response);
  }
}