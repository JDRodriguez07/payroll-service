package com.app.payroll_service.models;

import java.math.BigDecimal;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import lombok.Data;

@Entity
@Data
@Table(name = "payroll")
public class Payroll {

    /**
     * Primary key for the payroll table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payroll_id", nullable = false)
    private Long payrollId;

    /**
     * ID of the user (employee) associated with the payroll.
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * Number of days paid during the payroll period.
     */
    @Column(name = "paid_days", nullable = false)
    private int paidDays;

    /**
     * Start date of the payroll period.
     */
    @Column(name = "initial_period", nullable = false)
    private LocalDate initialPeriod;

    /**
     * End date of the payroll period.
     */
    @Column(name = "final_period", nullable = false)
    private LocalDate finalPeriod;

    /**
     * Total net salary to be paid.
     */
    @Column(name = "net_salary", nullable = false)
    private BigDecimal netSalary;

    /**
     * Status of the payroll (e.g., processed, pending, etc.).
     */
    @Column(name = "status", nullable = false)
    private String status;

    /**
     * One-to-many relationship with PayrollDeductions.
     * A payroll can have multiple deductions.
     */
    @OneToMany(mappedBy = "payroll", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<PayrollDeductions> payrollDeductions;

    /**
     * Timestamp of when the payroll record was created.
     */
    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    /**
     * Timestamp of the last update made to the payroll.
     */
    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;
}
