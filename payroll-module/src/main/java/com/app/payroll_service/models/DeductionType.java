package com.app.payroll_service.models;

import jakarta.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "deduction_type")
public class DeductionType {

    /**
     * Primary key for the deduction_type table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deduction_type_id", nullable = false)
    private Long deductionTypeId;

    /**
     * Name or description of the deduction type.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * One-to-many relationship with Deduction.
     * A deduction type can be associated with multiple deductions.
     * This field is ignored in JSON serialization to avoid recursion.
     */
    @OneToMany(mappedBy = "deductionType", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Deduction> deductions;

    /**
     * Percentage applied to the deduction.
     * Example: 15% should be stored as 0.1500
     */
    @Column(name = "percentage", precision = 5, scale = 4, nullable = false)
    private BigDecimal percentage;

    /**
     * Timestamp of when the deduction type was created.
     */
    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    /**
     * Timestamp of the last update to the deduction type.
     */
    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;
}
