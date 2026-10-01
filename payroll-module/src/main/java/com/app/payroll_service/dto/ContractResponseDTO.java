package com.app.payroll_service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for returning contract details.
 */
@Data
public class ContractResponseDTO {

    /**
     * Unique identifier of the contract.
     */
    private Long contractId;

    /**
     * Name of the contract type (e.g., Full-Time, Part-Time, Temporary).
     */
    private String contractTypeName;

    /**
     * Time of day the contract work begins.
     */
    private LocalTime startTime;

    /**
     * Time of day the contract work ends.
     */
    private LocalTime endTime;

    /**
     * Number of working hours per day defined in the contract.
     */
    private int dailyHours;

    /**
     * Current status of the contract (e.g., ACTIVE, TERMINATED).
     */
    private String status;

    /**
     * Date the contract started (hire date).
     */
    private LocalDate hireDate;

    /**
     * Date the contract ends or was terminated, if applicable.
     */
    private LocalDate terminationDate;

    /**
     * Salary defined in the contract.
     */
    private BigDecimal salary;
}
