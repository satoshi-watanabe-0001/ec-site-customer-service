package com.ecsite.customer.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class UnauthorizedCustomerAccessExceptionTest {

  @Test
  void constructorWithMessage() {
    String message = "Unauthorized access to customer profile";
    UnauthorizedCustomerAccessException exception =
        new UnauthorizedCustomerAccessException(message);

    assertNotNull(exception);
    assertEquals(message, exception.getMessage());
  }

  @Test
  void extendsRuntimeException() {
    UnauthorizedCustomerAccessException exception =
        new UnauthorizedCustomerAccessException("test message");
    assertTrue(exception instanceof RuntimeException);
  }
}
