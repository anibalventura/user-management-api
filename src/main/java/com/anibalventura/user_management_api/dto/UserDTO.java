package com.anibalventura.user_management_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
  @NotBlank(message = "El nombre es obligatorio")
  private String name;

  @Email(message = "Formato de correo inválido")
  private String email;

  @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "The password must be at least 8 characters long, contain at least one letter and one number")
  private String password;

  private List<PhoneDTO> phones;
}