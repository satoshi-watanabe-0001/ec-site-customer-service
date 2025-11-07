package com.ecsite.customer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhoneNumberUpdateRequest {

  @NotBlank(message = "Phone number type is required")
  @Pattern(
      regexp = "^(mobile|home|work)$",
      message = "Phone number type must be 'mobile', 'home', or 'work'")
  private String type;

  @NotBlank(message = "Phone number is required")
  @Pattern(
      regexp = "^\\d{2,4}-\\d{2,4}-\\d{4}$",
      message = "Phone number must be in format XX-XXXX-XXXX or XXX-XXXX-XXXX")
  private String number;

  private Boolean isDefault;
}
