package com.ecsite.customer.exception;

/**
 * Exception thrown when customer profile is not found
 *
 * @since 1.0
 */
public class ProfileNotFoundException extends RuntimeException {

  public ProfileNotFoundException(String message) {
    super(message);
  }

  public ProfileNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
