package com.ecsite.customer.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecsite.customer.entity.CustomerPhoneNumber;

@Repository
public interface CustomerPhoneNumberRepository extends JpaRepository<CustomerPhoneNumber, UUID> {}
