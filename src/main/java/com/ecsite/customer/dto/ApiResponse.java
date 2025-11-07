package com.ecsite.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generic API response wrapper
 *
 * <p>Provides a consistent response structure for all API endpoints
 *
 * @param <T> The type of data being returned
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

  private String status;
  private String message;
  private T data;
}
