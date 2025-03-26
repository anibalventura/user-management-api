package com.anibalventura.user_management_api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhoneDTO {
  @NotBlank(message = "Número es obligatorio")
  private String number;

  @NotBlank(message = "Código de ciudad es obligatorio")
  private String cityCode;

  @NotBlank(message = "Código de país es obligatorio")
  private String countryCode;
}