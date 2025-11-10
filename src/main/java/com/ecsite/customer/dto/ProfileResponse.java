package com.ecsite.customer.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for customer profile data
 *
 * <p>Contains all customer profile information including: - Profile ID and user ID - Basic
 * information (name) - Contact information (phone number) - Address information (postal code,
 * prefecture, city, street, building) - Timestamps (created at, updated at)
 *
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {

  private UUID id;
  private UUID userId;
  private String name;
  private String phoneNumber;
  private String postalCode;
  private String prefecture;
  private String city;
  private String streetAddress;
  private String building;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
