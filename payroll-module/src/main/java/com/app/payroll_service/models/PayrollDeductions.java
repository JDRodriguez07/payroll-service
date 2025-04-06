package com.app.payroll_service.models;

import jakarta.persistence.Entity;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "payroll_deductions")
public class PayrollDeductions {

    /**
     * Primary key for the payroll_deductions table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payroll_deduction_id", nullable = false)
    private Long payrollDeductionId;

    /**
     * Many-to-one relationship to the Deduction entity.
     * Each payroll deduction is linked to a specific deduction.
     */
    @ManyToOne
    @JoinColumn(name = "deduction_id", nullable = false)
    private Deduction deduction;

    /**
     * Many-to-one relationship to the Payroll entity.
     * Each payroll deduction is associated with a specific payroll.
     */
    @ManyToOne
    @JoinColumn(name = "payroll_id", nullable = false)
    private Payroll payroll;

    /**
     * Timestamp of when the payroll deduction record was created.
     */
    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    /**
     * Timestamp of the last update made to the payroll deduction.
     */
    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;
}
