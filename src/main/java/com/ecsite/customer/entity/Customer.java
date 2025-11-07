package com.ecsite.customer.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "name", nullable = false, length = 100)
  private String name;

  @Column(name = "status", nullable = false, length = 20)
  @Builder.Default
  private String status = "active";

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  @Column(name = "created_by", length = 100)
  private String createdBy;

  @Column(name = "updated_by", length = 100)
  private String updatedBy;

  @Version
  @Column(name = "version", nullable = false)
  @Builder.Default
  private Integer version = 0;

  @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
  private CustomerProfile profile;

  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private List<CustomerAddress> addresses = new ArrayList<>();

  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private List<CustomerPhoneNumber> phoneNumbers = new ArrayList<>();

  public void setProfile(CustomerProfile profile) {
    this.profile = profile;
    if (profile != null) {
      profile.setCustomer(this);
    }
  }

  public void addAddress(CustomerAddress address) {
    addresses.add(address);
    address.setCustomer(this);
  }

  public void removeAddress(CustomerAddress address) {
    addresses.remove(address);
    address.setCustomer(null);
  }

  public void addPhoneNumber(CustomerPhoneNumber phoneNumber) {
    phoneNumbers.add(phoneNumber);
    phoneNumber.setCustomer(this);
  }

  public void removePhoneNumber(CustomerPhoneNumber phoneNumber) {
    phoneNumbers.remove(phoneNumber);
    phoneNumber.setCustomer(null);
  }
}
