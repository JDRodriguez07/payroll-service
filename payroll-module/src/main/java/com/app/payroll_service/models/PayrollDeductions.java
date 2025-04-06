package com.app.payroll_service.models;

import jakarta.persistence.Entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "payroll_deductions")
public class PayrollDeductions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payroll_deduction_id", nullable = false)
    private Long payrollDeductionId;

    @ManyToOne
    @JoinColumn(name = "deduction_id", nullable = false)
    private Deduction deduction;

    @ManyToOne
    @JoinColumn(name = "payroll_id", nullable = false)
    private Payroll payroll;

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

}
