package com.ecsite.customer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * CustomerProfile entity representing customer profile information
 *
 * <p>This entity stores customer profile data including: - Basic information (name) - Contact
 * information (phone number) - Address information (postal code, prefecture, city, street,
 * building)
 *
 * @since 1.0
 */
@Entity
@Table(name = "customer_profiles", schema = "customer_schema")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerProfile {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;

  @Column(name = "user_id", nullable = false, unique = true)
  private UUID userId;

  @Column(name = "name", nullable = false, length = 100)
  private String name;

  @Column(name = "phone_number", length = 20)
  private String phoneNumber;

  @Column(name = "postal_code", length = 10)
  private String postalCode;

  @Column(name = "prefecture", length = 50)
  private String prefecture;

  @Column(name = "city", length = 100)
  private String city;

  @Column(name = "street_address", length = 200)
  private String streetAddress;

  @Column(name = "building", length = 100)
  private String building;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;
}
