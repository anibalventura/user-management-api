package com.anibalventura.user_management_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDTO {
  @NotBlank(message = "El correo es obligatorio")
  @Email(message = "Formato de correo inválido")
  @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "El correo debe tener un formato válido")
  private String email;

  @NotBlank(message = "La contraseña es obligatoria")
  private String password;
}