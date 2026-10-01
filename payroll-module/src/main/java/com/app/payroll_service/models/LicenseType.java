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

    /**
     * Primary key for the license_type table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "license_type_id", nullable = false)
    private Long licenseTypeId;

    /**
     * One-to-many relationship with License.
     * A license type can be associated with multiple licenses.
     */
    @OneToMany(mappedBy = "licenseType", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<License> licenses;

    /**
     * Description of the license type (e.g., medical leave, vacation, etc.).
     */
    @Column(name = "description", nullable = false)
    private String description;

    /**
     * Indicates if this license type should apply a salary discount.
     * true (1) = discounted, false (0) = not discounted.
     */
    @Column(name = "is_discount", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean isDiscount;

    /**
     * Timestamp of when the license type record was created.
     */
    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    /**
     * Timestamp of the last update made to the license type.
     */
    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;
}
