package com.anibalventura.user_management_api.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "DTO for user registration")
public class RegisterDTO {
  @NotBlank(message = "El nombre es obligatorio")
  @Schema(description = "User name", example = "John Doe")
  private String name;

  @NotBlank(message = "El correo es obligatorio")
  @Email(message = "Formato de correo inválido")
  @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "El correo debe tener un formato válido")
  @Schema(description = "User email", example = "user@example.com")
  private String email;

  @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "La contraseña debe contener una longitud mínima de 8 caracteres, contener al menos 1 letra, un número y un carácter especial")
  @Schema(description = "User password", example = "password123!")
  private String password;

  @Schema(description = "List of user phones")
  private List<PhoneDTO> phones;

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @Schema(description = "DTO for user phone")
  public static class PhoneDTO {
    @NotBlank(message = "Número es obligatorio")
    @Schema(description = "Phone number", example = "123456789")
    private String number;

    @NotBlank(message = "Código de ciudad es obligatorio")
    @Schema(description = "City code", example = "1")
    private String cityCode;

    @NotBlank(message = "Código de país es obligatorio")
    @Schema(description = "Country code", example = "1")
    private String countryCode;
  }
}