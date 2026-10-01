package com.app.payroll_service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * Data Transfer Object (DTO) for creating a new contract.
 */
@Data
public class CreateContractDTO {

    /**
     * ID of the schedule associated with the contract.
     * Cannot be null.
     */
    @NotNull(message = "Schedule ID is required.")
    private Long scheduleId;

    /**
     * ID of the contract type (e.g., Full-Time, Part-Time).
     * Cannot be null.
     */
    @NotNull(message = "Contract Type ID is required.")
    private Long contractTypeId;

    /**
     * Date the contract begins (hire date).
     * Cannot be null.
     */
    @NotNull(message = "Hire date is required.")
    private LocalDate hireDate;

    /**
     * Optional contract termination date, if known in advance.
     */
    private LocalDate terminationDate;

    /**
     * Salary assigned to the contract.
     * Must be greater than zero.
     */
    @NotNull(message = "Salary is required.")
    @DecimalMin(value = "0.01", message = "Salary must be greater than zero.")
    private BigDecimal salary;
}
