package com.ecsite.customer.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.ecsite.customer.dto.ProfileUpdateRequest;
import com.ecsite.customer.entity.Customer;
import com.ecsite.customer.service.CustomerProfileService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(
    controllers = CustomerProfileController.class,
    excludeAutoConfiguration = {
      org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
    })
@AutoConfigureMockMvc(addFilters = false)
class CustomerProfileControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @MockBean private CustomerProfileService customerProfileService;

  @Test
  void updateProfileSuccessJapaneseUrl() throws Exception {
    UUID customerId = UUID.randomUUID();
    ProfileUpdateRequest request = new ProfileUpdateRequest();
    request.setName("Test User");
    request.setStatus("active");

    Customer customer = new Customer();
    customer.setId(customerId);
    customer.setName("Test User");
    customer.setStatus("active");
    customer.setUpdatedAt(java.time.LocalDateTime.now());

    when(customerProfileService.updateCustomerProfile(
            eq(customerId), any(ProfileUpdateRequest.class), any(String.class)))
        .thenReturn(customer);

    mockMvc
        .perform(
            put("/api/v1/会員情報編集/{id}", customerId)
                .with(user(customerId.toString()).roles("USER"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("success"))
        .andExpect(jsonPath("$.data.id").value(customerId.toString()));
  }

  @Test
  void updateProfileSuccessEnglishUrl() throws Exception {
    UUID customerId = UUID.randomUUID();
    ProfileUpdateRequest request = new ProfileUpdateRequest();
    request.setName("Test User");
    request.setStatus("active");

    Customer customer = new Customer();
    customer.setId(customerId);
    customer.setName("Test User");
    customer.setStatus("active");
    customer.setUpdatedAt(java.time.LocalDateTime.now());

    when(customerProfileService.updateCustomerProfile(
            eq(customerId), any(ProfileUpdateRequest.class), any(String.class)))
        .thenReturn(customer);

    mockMvc
        .perform(
            put("/api/v1/customers/{id}/profile", customerId)
                .with(user(customerId.toString()).roles("USER"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("success"))
        .andExpect(jsonPath("$.data.id").value(customerId.toString()));
  }

  @Test
  void updateProfileValidationError() throws Exception {
    UUID customerId = UUID.randomUUID();
    ProfileUpdateRequest request = new ProfileUpdateRequest();

    mockMvc
        .perform(
            put("/api/v1/customers/{id}/profile", customerId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());
  }
}
