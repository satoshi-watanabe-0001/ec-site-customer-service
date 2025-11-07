package com.ecsite.customer.service;

import com.ecsite.customer.dto.ProfileResponse;
import com.ecsite.customer.dto.UpdateProfileRequest;
import com.ecsite.customer.entity.CustomerProfile;
import com.ecsite.customer.exception.ProfileNotFoundException;
import com.ecsite.customer.mapper.ProfileMapper;
import com.ecsite.customer.repository.CustomerProfileRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for customer profile management
 *
 * <p>Handles business logic for: - Retrieving customer profile information - Updating customer
 * profile information - Creating new customer profiles
 *
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileService {

  private final CustomerProfileRepository profileRepository;
  private final ProfileMapper profileMapper;

  /**
   * Get customer profile by user ID
   *
   * @param userId User ID
   * @return Profile response DTO
   * @throws ProfileNotFoundException if profile not found
   */
  @Transactional(readOnly = true)
  public ProfileResponse getProfile(UUID userId) {
    log.info("Retrieving profile for user ID: {}", userId);

    CustomerProfile profile =
        profileRepository
            .findByUserId(userId)
            .orElseThrow(
                () -> new ProfileNotFoundException("Profile not found for user ID: " + userId));

    return profileMapper.toResponse(profile);
  }

  /**
   * Update customer profile
   *
   * <p>If profile doesn't exist, creates a new one
   *
   * @param userId User ID
   * @param request Update profile request
   * @return Updated profile response DTO
   */
  @Transactional
  public ProfileResponse updateProfile(UUID userId, UpdateProfileRequest request) {
    log.info("Updating profile for user ID: {}", userId);

    CustomerProfile profile =
        profileRepository
            .findByUserId(userId)
            .orElseGet(
                () -> {
                  log.info("Profile not found for user ID: {}, creating new profile", userId);
                  return CustomerProfile.builder().userId(userId).build();
                });

    profile.setName(request.getName());
    profile.setPhoneNumber(request.getPhoneNumber());
    profile.setPostalCode(request.getPostalCode());
    profile.setPrefecture(request.getPrefecture());
    profile.setCity(request.getCity());
    profile.setStreetAddress(request.getStreetAddress());
    profile.setBuilding(request.getBuilding());

    CustomerProfile savedProfile = profileRepository.save(profile);
    log.info("Profile updated successfully for user ID: {}", userId);

    return profileMapper.toResponse(savedProfile);
  }
}
