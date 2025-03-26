package com.anibalventura.user_management_api.service;

import com.anibalventura.user_management_api.dto.RegisterDTO;
import com.anibalventura.user_management_api.model.User;
import com.anibalventura.user_management_api.model.Phone;
import com.anibalventura.user_management_api.repository.UserRepository;
import com.anibalventura.user_management_api.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
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

    userRepository.save(user);

    String token = jwtUtil.generateToken(user.getEmail());

    user.setToken(token);
    return user;
  }
}