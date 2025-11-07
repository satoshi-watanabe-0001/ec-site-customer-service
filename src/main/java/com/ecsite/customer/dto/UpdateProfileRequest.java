package com.ecsite.customer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for updating customer profile
 *
 * <p>Validation rules: - name: required, 1-100 characters - phoneNumber: optional, valid phone
 * format - postalCode: optional, valid postal code format - prefecture: optional, max 50 characters
 * - city: optional, max 100 characters - streetAddress: optional, max 200 characters - building:
 * optional, max 100 characters
 *
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfileRequest {

  @NotBlank(message = "Name is required")
  @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
  private String name;

  @Pattern(
      regexp = "^[0-9\\-+()\\s]*$",
      message =
          "Phone number must contain only numbers, hyphens, plus signs, parentheses, and spaces")
  @Size(max = 20, message = "Phone number must not exceed 20 characters")
  private String phoneNumber;

  @Pattern(regexp = "^[0-9\\-]*$", message = "Postal code must contain only numbers and hyphens")
  @Size(max = 10, message = "Postal code must not exceed 10 characters")
  private String postalCode;

  @Size(max = 50, message = "Prefecture must not exceed 50 characters")
  private String prefecture;

  @Size(max = 100, message = "City must not exceed 100 characters")
  private String city;

  @Size(max = 200, message = "Street address must not exceed 200 characters")
  private String streetAddress;

  @Size(max = 100, message = "Building must not exceed 100 characters")
  private String building;
}
