package com.ecsite.customer.mapper;

import com.ecsite.customer.dto.ProfileResponse;
import com.ecsite.customer.entity.CustomerProfile;
import org.springframework.stereotype.Component;

/**
 * Mapper class for converting between CustomerProfile entity and DTOs
 *
 * @since 1.0
 */
@Component
public class ProfileMapper {

  /**
   * Convert CustomerProfile entity to ProfileResponse DTO
   *
   * @param profile CustomerProfile entity
   * @return ProfileResponse DTO
   */
  public ProfileResponse toResponse(CustomerProfile profile) {
    if (profile == null) {
      return null;
    }

    return ProfileResponse.builder()
        .id(profile.getId())
        .userId(profile.getUserId())
        .name(profile.getName())
        .phoneNumber(profile.getPhoneNumber())
        .postalCode(profile.getPostalCode())
        .prefecture(profile.getPrefecture())
        .city(profile.getCity())
        .streetAddress(profile.getStreetAddress())
        .building(profile.getBuilding())
        .createdAt(profile.getCreatedAt())
        .updatedAt(profile.getUpdatedAt())
        .build();
  }
}
