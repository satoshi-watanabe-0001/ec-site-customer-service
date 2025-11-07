package com.ecsite.customer.repository;

import com.ecsite.customer.entity.CustomerPhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerPhoneNumberRepository extends JpaRepository<CustomerPhoneNumber, UUID> {
}
