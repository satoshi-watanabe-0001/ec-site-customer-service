package com.ecsite.customer.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.ecsite.customer.dto.ErrorResponse;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

  private GlobalExceptionHandler handler;

  @BeforeEach
  void setUp() {
    handler = new GlobalExceptionHandler();
  }

  @Test
  void handleCustomerNotFoundException() {
    CustomerNotFoundException ex = new CustomerNotFoundException("test-id");

    ResponseEntity<ErrorResponse> response = handler.handleCustomerNotFoundException(ex);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("error", response.getBody().getStatus());
    assertEquals("CUSTOMER_NOT_FOUND", response.getBody().getCode());
  }

  @Test
  void handleUnauthorizedCustomerAccessException() {
    UnauthorizedCustomerAccessException ex =
        new UnauthorizedCustomerAccessException("Unauthorized");

    ResponseEntity<ErrorResponse> response = handler.handleUnauthorizedCustomerAccessException(ex);

    assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("error", response.getBody().getStatus());
    assertEquals("INSUFFICIENT_PERMISSIONS", response.getBody().getCode());
  }

  @Test
  void handleValidationException() throws Exception {
    BindingResult bindingResult = mock(BindingResult.class);
    List<FieldError> fieldErrors = new ArrayList<>();
    fieldErrors.add(new FieldError("object", "field1", "error message 1"));
    fieldErrors.add(new FieldError("object", "field2", "error message 2"));

    when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);

    java.lang.reflect.Method realMethod =
        DummyController.class.getDeclaredMethod("dummyMethod", String.class);
    org.springframework.core.MethodParameter methodParameter =
        new org.springframework.core.MethodParameter(realMethod, 0);

    MethodArgumentNotValidException ex =
        new MethodArgumentNotValidException(methodParameter, bindingResult);

    ResponseEntity<ErrorResponse> response = handler.handleValidationException(ex);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("error", response.getBody().getStatus());
    assertEquals("VALIDATION_ERROR", response.getBody().getCode());
    assertNotNull(response.getBody().getErrors());
    assertEquals(2, response.getBody().getErrors().size());
  }

  private static final class DummyController {
    @SuppressWarnings("unused")
    void dummyMethod(String arg) {}
  }

  @Test
  void handleAuthenticationException() {
    BadCredentialsException ex = new BadCredentialsException("Bad credentials");

    ResponseEntity<ErrorResponse> response = handler.handleAuthenticationException(ex);

    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("error", response.getBody().getStatus());
    assertEquals("AUTHENTICATION_REQUIRED", response.getBody().getCode());
  }

  @Test
  void handleAccessDeniedException() {
    AccessDeniedException ex = new AccessDeniedException("Access denied");

    ResponseEntity<ErrorResponse> response = handler.handleAccessDeniedException(ex);

    assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("error", response.getBody().getStatus());
    assertEquals("INSUFFICIENT_PERMISSIONS", response.getBody().getCode());
  }

  @Test
  void handleGeneralException() {
    Exception ex = new RuntimeException("Unexpected error");

    ResponseEntity<ErrorResponse> response = handler.handleGeneralException(ex);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("error", response.getBody().getStatus());
    assertEquals("INTERNAL_SERVER_ERROR", response.getBody().getCode());
  }
}
