package com.app.payroll_service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

/**
 * Data Transfer Object (DTO) for updating an existing contract.
 */
@Data
public class UpdateContractDTO {

    /**
     * ID of the updated schedule associated with the contract (optional).
     */
    private Long scheduleId;

    /**
     * Updated hire date of the contract (optional).
     */
    private LocalDate hireDate;

    /**
     * Updated termination date of the contract (optional).
     */
    private LocalDate terminationDate;

    /**
     * Updated salary for the contract.
     * Must be greater than zero if provided.
     */
    @DecimalMin(value = "0.01", message = "Salary must be greater than zero.")
    private BigDecimal salary;
    
}
