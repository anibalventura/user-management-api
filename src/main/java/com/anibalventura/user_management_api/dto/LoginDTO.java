package com.anibalventura.user_management_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDTO {
  @NotBlank(message = "El correo es obligatorio")
  @Email(message = "Formato de correo inválido")
  private String email;

  @NotBlank(message = "La contraseña es obligatoria")
  private String password;
}