package com.app.payroll_service.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table
public class Payroll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payroll_id", nullable = false)
    private Long payrollId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "paid_days", nullable = false)
    private int paidDays;

    @Column(name = "initial_period", nullable = false)
    private Date initialPeriod;

    @Column(name = "final_period", nullable = false)
    private Date finalPeriod;

    @Column(name = "net_salary", nullable = false)
    private BigDecimal netSalary;

    @Column(name = "status", nullable = false)
    private String status;

    // Relationship with payroll_deductions
    @OneToMany(mappedBy = "payroll", cascade = CascadeType.ALL)
    private List<PayrollDeductions> payrollDeductions;

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

}
