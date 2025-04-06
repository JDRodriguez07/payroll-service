package com.app.payroll_service.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "license")
public class License {

    /**
     * Primary key for the license table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "license_id", nullable = false)
    private Long licenseId;

    /**
     * Many-to-one relationship to the license type.
     * Each license must be associated with a specific license type.
     */
    @ManyToOne
    @JoinColumn(name = "license_type_id", nullable = false)
    private LicenseType licenseType;

    /**
     * ID of the user associated with this license.
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * Start date of the license period.
     */
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    /**
     * End date of the license period.
     */
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    /**
     * Total number of days covered by the license.
     */
    @Column(name = "days", nullable = false)
    private int days;

    /**
     * Current status of the license (e.g., approved, pending, rejected).
     */
    @Column(name = "status", nullable = false)
    private String status;

    /**
     * Timestamp of when the license was created.
     */
    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    /**
     * Timestamp of the last update made to the license.
     */
    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;
}
