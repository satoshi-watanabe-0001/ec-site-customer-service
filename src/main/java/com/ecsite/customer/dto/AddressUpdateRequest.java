package com.ecsite.customer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressUpdateRequest {

    @NotBlank(message = "Address type is required")
    @Pattern(regexp = "^(home|work|other)$", message = "Address type must be 'home', 'work', or 'other'")
    private String type;

    @NotBlank(message = "Postal code is required")
    @Pattern(regexp = "^\\d{3}-\\d{4}$", message = "Postal code must be in format XXX-XXXX")
    private String postalCode;

    @NotBlank(message = "Prefecture is required")
    @Size(max = 10, message = "Prefecture must not exceed 10 characters")
    private String prefecture;

    @NotBlank(message = "City is required")
    @Size(max = 50, message = "City must not exceed 50 characters")
    private String city;

    @NotBlank(message = "Address line 1 is required")
    @Size(max = 100, message = "Address line 1 must not exceed 100 characters")
    private String addressLine1;

    @Size(max = 100, message = "Address line 2 must not exceed 100 characters")
    private String addressLine2;

    private Boolean isDefault;
}
