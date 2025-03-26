package com.anibalventura.user_management_api.service;

import com.anibalventura.user_management_api.dto.UserDTO;
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

  public User registerUser(UserDTO userDTO) {
    if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
      throw new IllegalArgumentException("El correo ya registrado");
    }

    List<Phone> phones = userDTO.getPhones().stream()
            .map(phoneDTO -> Phone.builder()
                    .number(phoneDTO.getNumber())
                    .cityCode(phoneDTO.getCityCode())
                    .countryCode(phoneDTO.getCountryCode())
                    .build())
            .collect(Collectors.toList());

    User user = User.builder()
            .name(userDTO.getName())
            .email(userDTO.getEmail())
            .password(passwordEncoder.encode(userDTO.getPassword()))
            .phones(phones)
            .build();

    phones.forEach(phone -> phone.setUser(user));

    userRepository.save(user);

    String token = jwtUtil.generateToken(user.getEmail());

    user.setToken(token);
    return user;
  }
}