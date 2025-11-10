package com.ecsite.customer.exception;

public class UnauthorizedCustomerAccessException extends RuntimeException {

  public UnauthorizedCustomerAccessException(String message) {
    super(message);
  }
}
