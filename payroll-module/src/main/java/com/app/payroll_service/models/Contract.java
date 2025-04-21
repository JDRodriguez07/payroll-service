package com.app.payroll_service.models;

import java.math.BigDecimal;
import java.util.Date;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "contract")
public class Contract {

    /**
     * Primary key for the contract table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_id", nullable = false)
    private Long contractId;

    /**
     * Reference to the associated work schedule.
     */
    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    /**
     * Reference to the type of contract.
     */
    @ManyToOne
    @JoinColumn(name = "contract_type_id", nullable = false)
    private ContractType contractType;

    /**
     * Date when the contract started.
     */
    @Column(name = "hire_date", nullable = false)
    private Date hireDate;

    /**
     * Date when the contract ended (nullable for ongoing contracts).
     */
    @Column(name = "termination_date")
    @Temporal(TemporalType.DATE)
    private Date terminationDate;

    /**
     * Number of working hours per day defined in the contract.
     */
    @Column(name = "daily_hours", nullable = false)
    private int dailyHours;

    /**
     * Monthly salary defined in the contract.
     */
    @Column(name = "salary", nullable = false, precision = 10, scale = 2)
    private BigDecimal salary;

    /**
     * Status of the contract (active or inactive).
     */
    @Column(name = "status", nullable = false)
    private String status;

    /**
     * Timestamp when the contract record was created.
     */
    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    /**
     * Timestamp when the contract record was last updated.
     */
    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;
}
