package com.anibalventura.user_management_api.service;

import com.anibalventura.user_management_api.dto.UserDTO;
import com.anibalventura.user_management_api.model.User;
import com.anibalventura.user_management_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public User registerUser(UserDTO userDTO) {
    if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
      throw new IllegalArgumentException("El correo ya registrado");
    }

    User user = User.builder()
        .id(UUID.randomUUID())
        .name(userDTO.getName())
        .email(userDTO.getEmail())
        .password(passwordEncoder.encode(userDTO.getPassword()))
        .build();

    return userRepository.save(user);
  }
}
