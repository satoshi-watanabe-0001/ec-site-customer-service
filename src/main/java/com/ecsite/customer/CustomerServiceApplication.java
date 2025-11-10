package com.ecsite.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for EC Site Customer Service
 *
 * <p>This service handles customer profile management including: - Profile information retrieval -
 * Profile information updates - Customer data management
 *
 * @since 1.0
 */
@SpringBootApplication
public class CustomerServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(CustomerServiceApplication.class, args);
  }
}
