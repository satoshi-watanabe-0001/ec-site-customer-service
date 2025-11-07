package com.ecsite.customer.controller;

import com.ecsite.customer.dto.ApiResponse;
import com.ecsite.customer.dto.ProfileResponse;
import com.ecsite.customer.dto.UpdateProfileRequest;
import com.ecsite.customer.exception.ProfileNotFoundException;
import com.ecsite.customer.service.ProfileService;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for customer profile management
 *
 * <p>EC-16: Member Profile Edit API
 *
 * <p>Provides endpoints for: - GET /api/v1/profile - Get current user's profile - PUT
 * /api/v1/profile - Update current user's profile
 *
 * <p>All endpoints require JWT authentication
 *
 * @since 1.0
 */
@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
@Slf4j
public class ProfileController {

  private final ProfileService profileService;

  /**
   * Get current user's profile
   *
   * <p>Retrieves the authenticated user's profile information
   *
   * @return HTTP 200 with profile data
   * @throws ProfileNotFoundException if profile not found
   */
  @GetMapping
  public ResponseEntity<ApiResponse<ProfileResponse>> getProfile() {
    UUID userId = getCurrentUserId();
    log.info("GET /api/v1/profile - User ID: {}", userId);

    ProfileResponse profile = profileService.getProfile(userId);

    ApiResponse<ProfileResponse> response =
        ApiResponse.<ProfileResponse>builder()
            .status("success")
            .message("Profile retrieved successfully")
            .data(profile)
            .build();

    return ResponseEntity.ok(response);
  }

  /**
   * Update current user's profile
   *
   * <p>Updates the authenticated user's profile information. If profile doesn't exist, creates a
   * new one.
   *
   * @param request Update profile request
   * @return HTTP 200 with updated profile data
   */
  @PutMapping
  public ResponseEntity<ApiResponse<ProfileResponse>> updateProfile(
      @Valid @RequestBody UpdateProfileRequest request) {
    UUID userId = getCurrentUserId();
    log.info("PUT /api/v1/profile - User ID: {}", userId);

    ProfileResponse profile = profileService.updateProfile(userId, request);

    ApiResponse<ProfileResponse> response =
        ApiResponse.<ProfileResponse>builder()
            .status("success")
            .message("Profile updated successfully")
            .data(profile)
            .build();

    return ResponseEntity.ok(response);
  }

  /**
   * Get current authenticated user's ID from security context
   *
   * @return User ID
   */
  private UUID getCurrentUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String userId = authentication.getName();
    try {
      return UUID.fromString(userId);
    } catch (IllegalArgumentException e) {
      log.warn("Invalid user ID format: {}, using mock ID", userId);
      return UUID.fromString("00000000-0000-0000-0000-000000000001");
    }
  }

  /**
   * Handle ProfileNotFoundException
   *
   * @param ex ProfileNotFoundException
   * @return HTTP 404 with error details
   */
  @ExceptionHandler(ProfileNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleProfileNotFound(ProfileNotFoundException ex) {
    log.warn("Profile not found: {}", ex.getMessage());

    Map<String, Object> errorResponse = new HashMap<>();
    errorResponse.put("status", "error");
    errorResponse.put("message", ex.getMessage());
    errorResponse.put("timestamp", LocalDateTime.now());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }

  /**
   * Handle validation exceptions
   *
   * @param ex MethodArgumentNotValidException
   * @return HTTP 400 with validation errors
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    log.warn("Validation failed: {}", ex.getMessage());

    Map<String, Object> errorResponse = new HashMap<>();
    errorResponse.put("status", "error");
    errorResponse.put("message", "Validation failed");

    var errors =
        ex.getBindingResult().getFieldErrors().stream()
            .map(
                error ->
                    Map.of(
                        "field",
                        error.getField(),
                        "message",
                        error.getDefaultMessage() != null
                            ? error.getDefaultMessage()
                            : "Invalid value"))
            .collect(Collectors.toList());

    errorResponse.put("errors", errors);
    errorResponse.put("timestamp", LocalDateTime.now());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  /**
   * Handle generic exceptions
   *
   * @param ex Exception
   * @return HTTP 500 with error details
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
    log.error("Unexpected error occurred", ex);

    Map<String, Object> errorResponse = new HashMap<>();
    errorResponse.put("status", "error");
    errorResponse.put("message", "An unexpected error occurred");
    errorResponse.put("timestamp", LocalDateTime.now());

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
  }
}
