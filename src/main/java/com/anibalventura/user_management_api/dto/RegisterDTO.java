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
public class RegisterDTO {
  @NotBlank(message = "El nombre es obligatorio")
  private String name;

  @NotBlank(message = "El correo es obligatorio")
  @Email(message = "Formato de correo inválido")
  private String email;

  @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "The password must be at least 8 characters long, contain at least one letter and one number")
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