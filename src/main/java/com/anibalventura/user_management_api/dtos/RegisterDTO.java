package com.anibalventura.user_management_api.dtos;

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
public class RegisterDTO {
  @NotBlank(message = "El nombre es obligatorio")
  private String name;

  @NotBlank(message = "El correo es obligatorio")
  @Email(message = "Formato de correo inválido")
  @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "El correo debe tener un formato válido")
  private String email;

  @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "La contraseña debe contener una longitud mínima de 8 caracteres, contener al menos 1 letra, un número y un carácter especial")
  private String password;

  private List<PhoneDTO> phones;

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class PhoneDTO {
    @NotBlank(message = "Número es obligatorio")
    private String number;

    @NotBlank(message = "Código de ciudad es obligatorio")
    private String cityCode;

    @NotBlank(message = "Código de país es obligatorio")
    private String countryCode;
  }
}