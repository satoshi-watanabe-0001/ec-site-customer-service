package com.ecsite.customer.exception;

import com.ecsite.customer.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCustomerNotFoundException(CustomerNotFoundException ex) {
        log.warn("Customer not found: {}", ex.getMessage());
        
        ErrorResponse response = ErrorResponse.builder()
                .status("error")
                .message("指定された顧客が見つかりません")
                .code("CUSTOMER_NOT_FOUND")
                .build();
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(UnauthorizedCustomerAccessException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedCustomerAccessException(
            UnauthorizedCustomerAccessException ex) {
        log.warn("Unauthorized access attempt: {}", ex.getMessage());
        
        ErrorResponse response = ErrorResponse.builder()
                .status("error")
                .message("この操作を実行する権限がありません")
                .code("INSUFFICIENT_PERMISSIONS")
                .build();
        
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        log.warn("Validation error: {}", ex.getMessage());
        
        List<ErrorResponse.FieldError> fieldErrors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.add(ErrorResponse.FieldError.builder()
                    .field(error.getField())
                    .message(error.getDefaultMessage())
                    .build());
        }
        
        ErrorResponse response = ErrorResponse.builder()
                .status("error")
                .message("入力値が不正です")
                .code("VALIDATION_ERROR")
                .errors(fieldErrors)
                .build();
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex) {
        log.warn("Authentication error: {}", ex.getMessage());
        
        ErrorResponse response = ErrorResponse.builder()
                .status("error")
                .message("認証が必要です")
                .code("AUTHENTICATION_REQUIRED")
                .build();
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
        log.warn("Access denied: {}", ex.getMessage());
        
        ErrorResponse response = ErrorResponse.builder()
                .status("error")
                .message("この操作を実行する権限がありません")
                .code("INSUFFICIENT_PERMISSIONS")
                .build();
        
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        log.error("Unexpected error occurred", ex);
        
        ErrorResponse response = ErrorResponse.builder()
                .status("error")
                .message("サーバー内部エラーが発生しました")
                .code("INTERNAL_SERVER_ERROR")
                .build();
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
