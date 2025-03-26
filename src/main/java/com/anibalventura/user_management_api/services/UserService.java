package com.anibalventura.user_management_api.services;

import com.anibalventura.user_management_api.dtos.LoginDTO;
import com.anibalventura.user_management_api.dtos.RegisterDTO;
import com.anibalventura.user_management_api.models.User;
import com.anibalventura.user_management_api.models.Phone;
import com.anibalventura.user_management_api.repositories.UserRepository;
import com.anibalventura.user_management_api.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;

  public User registerUser(RegisterDTO registerDTO) {
    if (userRepository.findByEmail(registerDTO.getEmail()).isPresent()) {
      throw new IllegalArgumentException("El correo ya registrado");
    }

    List<Phone> phones = registerDTO.getPhones().stream()
            .map(phoneDTO -> Phone.builder()
                    .number(phoneDTO.getNumber())
                    .cityCode(phoneDTO.getCityCode())
                    .countryCode(phoneDTO.getCountryCode())
                    .build())
            .collect(Collectors.toList());

    User user = User.builder()
            .name(registerDTO.getName())
            .email(registerDTO.getEmail())
            .password(passwordEncoder.encode(registerDTO.getPassword()))
            .phones(phones)
            .build();

    phones.forEach(phone -> phone.setUser(user));

    String token = jwtUtil.generateToken(user.getEmail());
    user.setToken(token);

    userRepository.save(user);

    return user;
  }

  public User loginUser(LoginDTO loginDTO) {
    Optional<User> optionalUser = userRepository.findByEmail(loginDTO.getEmail());

    if (optionalUser.isEmpty()) {
      throw new IllegalArgumentException("Credenciales incorrectas");
    }

    User user = optionalUser.get();

    if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
      throw new IllegalArgumentException("Credenciales incorrectas");
    }

    // Update lastLogin and token
    user.setLastLogin(LocalDateTime.now(ZoneId.systemDefault()));
    String token = jwtUtil.generateToken(user.getEmail());
    user.setToken(token);

    userRepository.save(user);

    return user;
  }

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }
}