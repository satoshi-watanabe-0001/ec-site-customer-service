package com.ecsite.customer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ecsite.customer.dto.AddressUpdateRequest;
import com.ecsite.customer.dto.PhoneNumberUpdateRequest;
import com.ecsite.customer.dto.ProfileUpdateRequest;
import com.ecsite.customer.entity.Customer;
import com.ecsite.customer.entity.CustomerProfile;
import com.ecsite.customer.exception.CustomerNotFoundException;
import com.ecsite.customer.repository.CustomerRepository;

@ExtendWith(MockitoExtension.class)
class CustomerProfileServiceTest {

  @Mock private CustomerRepository customerRepository;

  private CustomerProfileService customerProfileService;

  private UUID customerId;
  private Customer customer;
  private CustomerProfile customerProfile;

  @BeforeEach
  void setUp() {
    customerProfileService = new CustomerProfileService(customerRepository);

    customerId = UUID.randomUUID();
    customer = new Customer();
    customer.setId(customerId);
    customer.setName("Test Customer");
    customer.setStatus("active");
    customer.setAddresses(new java.util.ArrayList<>());
    customer.setPhoneNumbers(new java.util.ArrayList<>());

    customerProfile = new CustomerProfile();
    customerProfile.setId(UUID.randomUUID());
    customerProfile.setCustomer(customer);
    customerProfile.setFirstName("Test");
    customerProfile.setLastName("User");

    customer.setProfile(customerProfile);
  }

  @Test
  void updateCustomerProfileSuccess() {
    ProfileUpdateRequest request = new ProfileUpdateRequest();
    request.setName("Updated Name");
    request.setStatus("active");

    when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
    when(customerRepository.save(any(Customer.class))).thenReturn(customer);

    Customer result =
        customerProfileService.updateCustomerProfile(customerId, request, customerId.toString());

    assertNotNull(result);
    verify(customerRepository).findById(customerId);
    verify(customerRepository).save(any(Customer.class));
  }

  @Test
  void updateCustomerProfileCustomerNotFound() {
    ProfileUpdateRequest request = new ProfileUpdateRequest();
    request.setName("Updated Name");

    when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

    assertThrows(
        CustomerNotFoundException.class,
        () ->
            customerProfileService.updateCustomerProfile(
                customerId, request, customerId.toString()));
  }

  @Test
  void findCustomerByIdSuccess() {
    when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

    Customer result = customerProfileService.findCustomerById(customerId);

    assertNotNull(result);
    assertEquals(customerId, result.getId());
    verify(customerRepository).findById(customerId);
  }

  @Test
  void findCustomerByIdNotFound() {
    when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

    assertThrows(
        CustomerNotFoundException.class, () -> customerProfileService.findCustomerById(customerId));
  }

  @Test
  void updateCustomerProfileWithFirstAndLastName() {
    ProfileUpdateRequest request = new ProfileUpdateRequest();
    request.setName("Updated Name");
    request.setFirstName("John");
    request.setLastName("Doe");

    when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
    when(customerRepository.save(any(Customer.class))).thenReturn(customer);

    Customer result =
        customerProfileService.updateCustomerProfile(customerId, request, customerId.toString());

    assertNotNull(result);
    verify(customerRepository).save(any(Customer.class));
  }

  @Test
  void updateCustomerProfileWithAddresses() {
    ProfileUpdateRequest request = new ProfileUpdateRequest();
    request.setName("Updated Name");

    AddressUpdateRequest addressRequest = new AddressUpdateRequest();
    addressRequest.setType("home");
    addressRequest.setPostalCode("100-0001");
    addressRequest.setPrefecture("東京都");
    addressRequest.setCity("千代田区");
    addressRequest.setAddressLine1("千代田1-1-1");
    addressRequest.setIsDefault(true);

    request.setAddresses(Arrays.asList(addressRequest));

    when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
    when(customerRepository.save(any(Customer.class))).thenReturn(customer);

    Customer result =
        customerProfileService.updateCustomerProfile(customerId, request, customerId.toString());

    assertNotNull(result);
    verify(customerRepository).save(any(Customer.class));
  }

  @Test
  void updateCustomerProfileWithPhoneNumbers() {
    ProfileUpdateRequest request = new ProfileUpdateRequest();
    request.setName("Updated Name");

    PhoneNumberUpdateRequest phoneRequest = new PhoneNumberUpdateRequest();
    phoneRequest.setType("mobile");
    phoneRequest.setNumber("090-1234-5678");
    phoneRequest.setIsDefault(true);

    request.setPhoneNumbers(Arrays.asList(phoneRequest));

    when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
    when(customerRepository.save(any(Customer.class))).thenReturn(customer);

    Customer result =
        customerProfileService.updateCustomerProfile(customerId, request, customerId.toString());

    assertNotNull(result);
    verify(customerRepository).save(any(Customer.class));
  }

  @Test
  void updateCustomerProfileWithStatusChange() {
    ProfileUpdateRequest request = new ProfileUpdateRequest();
    request.setName("Updated Name");
    request.setStatus("inactive");

    when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
    when(customerRepository.save(any(Customer.class))).thenReturn(customer);

    Customer result =
        customerProfileService.updateCustomerProfile(customerId, request, customerId.toString());

    assertNotNull(result);
    verify(customerRepository).save(any(Customer.class));
  }
}
