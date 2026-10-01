package com.app.payroll_service.models;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "license_type")
public class LicenseType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "license_type_id", nullable = false)
    private Long licenseTypeId;

    @OneToMany(mappedBy = "licenseType", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<License> licenses;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "is_discount", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean isDiscount;

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

}
