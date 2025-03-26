package com.anibalventura.user_management_api.services;

import com.anibalventura.user_management_api.dtos.PaginatedResponseDTO;
import com.anibalventura.user_management_api.models.User;
import com.anibalventura.user_management_api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  public PaginatedResponseDTO<User> getAllUsers(Pageable pageable) {
    Page<User> users = userRepository.findAll(pageable);

    return new PaginatedResponseDTO<>(
            users.getContent(),
            users.getNumber(),
            users.getSize(),
            users.getTotalElements(),
            users.getTotalPages(),
            users.isLast()
    );
  }
}