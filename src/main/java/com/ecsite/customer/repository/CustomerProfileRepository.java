package com.ecsite.customer.repository;

import com.ecsite.customer.entity.CustomerProfile;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for CustomerProfile entity
 *
 * <p>Provides database access methods for customer profile operations
 *
 * @since 1.0
 */
@Repository
public interface CustomerProfileRepository extends JpaRepository<CustomerProfile, UUID> {

  /**
   * Find customer profile by user ID
   *
   * @param userId User ID
   * @return Optional containing the customer profile if found
   */
  Optional<CustomerProfile> findByUserId(UUID userId);

  /**
   * Check if customer profile exists for user ID
   *
   * @param userId User ID
   * @return true if profile exists, false otherwise
   */
  boolean existsByUserId(UUID userId);
}
