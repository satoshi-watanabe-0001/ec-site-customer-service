package com.ecsite.customer.controller;

import com.ecsite.customer.dto.ProfileUpdateRequest;
import com.ecsite.customer.dto.ProfileUpdateResponse;
import com.ecsite.customer.entity.Customer;
import com.ecsite.customer.service.CustomerProfileService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Validated
@Slf4j
public class CustomerProfileController {

    private final CustomerProfileService customerProfileService;

    public CustomerProfileController(CustomerProfileService customerProfileService) {
        this.customerProfileService = customerProfileService;
    }

    @PutMapping({"/api/v1/会員情報編集/{id}", "/api/v1/customers/{id}/profile"})
    @PreAuthorize("hasRole('USER') and #id.toString() == authentication.name or hasRole('ADMIN')")
    public ResponseEntity<ProfileUpdateResponse> updateProfile(
            @PathVariable UUID id,
            @RequestBody @Valid ProfileUpdateRequest request,
            Authentication authentication) {

        log.info("Received profile update request - customerId: {}, user: {}", 
                id, authentication.getName());

        String currentUserId = authentication.getName();
        Customer updatedCustomer = customerProfileService.updateCustomerProfile(id, request, currentUserId);

        ProfileUpdateResponse response = ProfileUpdateResponse.builder()
                .status("success")
                .message("会員情報編集が正常に更新されました")
                .data(ProfileUpdateResponse.ProfileData.builder()
                        .id(updatedCustomer.getId().toString())
                        .name(updatedCustomer.getName())
                        .updatedAt(updatedCustomer.getUpdatedAt().toString())
                        .build())
                .build();

        return ResponseEntity.ok(response);
    }
}
