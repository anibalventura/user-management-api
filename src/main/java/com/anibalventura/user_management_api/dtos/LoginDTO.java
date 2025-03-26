package com.anibalventura.user_management_api.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO for user login")
public class LoginDTO {
  @NotBlank(message = "El correo es obligatorio")
  @Email(message = "Formato de correo inválido")
  @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "El correo debe tener un formato válido")
  @Schema(description = "User email", example = "user@example.com")
  private String email;

  @NotBlank(message = "La contraseña es obligatoria")
  @Schema(description = "User password", example = "password123")
  private String password;
}