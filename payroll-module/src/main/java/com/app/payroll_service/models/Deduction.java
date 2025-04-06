package com.app.payroll_service.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "deductions")
public class Deduction {

    /**
     * Primary key for the deductions table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deduction_id", nullable = false)
    private Long deductionId;

    /**
     * Many-to-one relationship with the deduction type.
     * Each deduction must be associated with one deduction type.
     */
    @ManyToOne
    @JoinColumn(name = "deduction_type_id", nullable = false)
    private DeductionType deductionType;

    /**
     * The amount of the deduction.
     */
    @Column(name = "amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal amount;

    /**
     * One-to-many relationship with payroll deductions.
     * This field is ignored in JSON serialization to prevent circular references.
     */
    @OneToMany(mappedBy = "deduction", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<PayrollDeductions> payrollDeductions;

    /**
     * Timestamp when the deduction was created.
     */
    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    /**
     * Timestamp when the deduction was last updated.
     */
    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;
}
