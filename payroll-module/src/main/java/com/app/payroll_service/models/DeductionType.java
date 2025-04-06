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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deduction_type_id", nullable = false)
    private Long deductionTypeId;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "deductionType", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Deduction> deductions;

    // Percentage represented as a decimal. For example: 15% = 0.1500
    @Column(name = "percentage", precision = 5, scale = 4, nullable = false)
    private BigDecimal percentage;

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;
}
