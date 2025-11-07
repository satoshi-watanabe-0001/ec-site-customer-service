package com.ecsite.customer.exception;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CustomerNotFoundExceptionTest {

  @Test
  void constructorWithMessage() {
    String customerId = "123e4567-e89b-12d3-a456-426614174000";
    CustomerNotFoundException exception = new CustomerNotFoundException(customerId);

    assertNotNull(exception);
    assertTrue(exception.getMessage().contains(customerId));
  }

  @Test
  void extendsRuntimeException() {
    CustomerNotFoundException exception = new CustomerNotFoundException("test-id");
    assertTrue(exception instanceof RuntimeException);
  }
}
