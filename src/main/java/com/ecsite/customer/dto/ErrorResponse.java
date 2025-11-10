package com.ecsite.customer.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {

  @Builder.Default private String status = "error";

  private String message;

  private String code;

  private List<FieldError> errors;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class FieldError {
    private String field;
    private String message;
  }
}
