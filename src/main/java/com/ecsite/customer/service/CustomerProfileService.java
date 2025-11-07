package com.ecsite.customer.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecsite.customer.dto.AddressUpdateRequest;
import com.ecsite.customer.dto.PhoneNumberUpdateRequest;
import com.ecsite.customer.dto.ProfileUpdateRequest;
import com.ecsite.customer.entity.Customer;
import com.ecsite.customer.entity.CustomerAddress;
import com.ecsite.customer.entity.CustomerPhoneNumber;
import com.ecsite.customer.entity.CustomerProfile;
import com.ecsite.customer.exception.CustomerNotFoundException;
import com.ecsite.customer.repository.CustomerRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@Slf4j
public class CustomerProfileService {

  private final CustomerRepository customerRepository;

  public CustomerProfileService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Transactional
  public Customer updateCustomerProfile(
      UUID customerId, ProfileUpdateRequest request, String currentUserId) {
    long startTime = System.currentTimeMillis();
    log.info(
        "Customer profile update started - customerId: {}, userId: {}", customerId, currentUserId);

    Customer customer =
        customerRepository
            .findById(customerId)
            .orElseThrow(
                () -> {
                  log.warn("Customer not found - customerId: {}", customerId);
                  return new CustomerNotFoundException(customerId.toString());
                });

    updateBasicInfo(customer, request, currentUserId);
    updateProfile(customer, request);
    updateAddresses(customer, request);
    updatePhoneNumbers(customer, request);

    Customer savedCustomer = customerRepository.save(customer);

    long processingTime = System.currentTimeMillis() - startTime;
    log.info(
        "Customer profile updated successfully - customerId: {}, processingTime: {}ms",
        customerId,
        processingTime);

    return savedCustomer;
  }

  private void updateBasicInfo(
      Customer customer, ProfileUpdateRequest request, String currentUserId) {
    customer.setName(request.getName());

    if (request.getStatus() != null) {
      customer.setStatus(request.getStatus());
    }

    customer.setUpdatedBy(currentUserId);
  }

  private void updateProfile(Customer customer, ProfileUpdateRequest request) {
    if (request.getFirstName() != null || request.getLastName() != null) {
      CustomerProfile profile = customer.getProfile();

      if (profile == null) {
        profile = CustomerProfile.builder().customer(customer).build();
        customer.setProfile(profile);
      }

      if (request.getFirstName() != null) {
        profile.setFirstName(request.getFirstName());
      }

      if (request.getLastName() != null) {
        profile.setLastName(request.getLastName());
      }
    }
  }

  private void updateAddresses(Customer customer, ProfileUpdateRequest request) {
    if (request.getAddresses() != null && !request.getAddresses().isEmpty()) {
      customer.getAddresses().clear();

      for (AddressUpdateRequest addressRequest : request.getAddresses()) {
        CustomerAddress address =
            CustomerAddress.builder()
                .customer(customer)
                .type(addressRequest.getType())
                .postalCode(addressRequest.getPostalCode())
                .prefecture(addressRequest.getPrefecture())
                .city(addressRequest.getCity())
                .addressLine1(addressRequest.getAddressLine1())
                .addressLine2(addressRequest.getAddressLine2())
                .isDefault(
                    addressRequest.getIsDefault() != null ? addressRequest.getIsDefault() : false)
                .build();

        customer.addAddress(address);
      }
    }
  }

  private void updatePhoneNumbers(Customer customer, ProfileUpdateRequest request) {
    if (request.getPhoneNumbers() != null && !request.getPhoneNumbers().isEmpty()) {
      customer.getPhoneNumbers().clear();

      for (PhoneNumberUpdateRequest phoneRequest : request.getPhoneNumbers()) {
        CustomerPhoneNumber phoneNumber =
            CustomerPhoneNumber.builder()
                .customer(customer)
                .type(phoneRequest.getType())
                .number(phoneRequest.getNumber())
                .isDefault(
                    phoneRequest.getIsDefault() != null ? phoneRequest.getIsDefault() : false)
                .build();

        customer.addPhoneNumber(phoneNumber);
      }
    }
  }

  public Customer findCustomerById(UUID customerId) {
    return customerRepository
        .findById(customerId)
        .orElseThrow(() -> new CustomerNotFoundException(customerId.toString()));
  }
}
