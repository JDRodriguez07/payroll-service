package com.app.payroll_service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateContractDTO {

    @NotNull(message = "Schedule ID is required.")
    private Long scheduleId;

    @NotNull(message = "Contract Type ID is required.")
    private Long contractTypeId;

    @NotNull(message = "Hire date is required.")
    private LocalDate hireDate;

    private LocalDate terminationDate;

    @NotNull(message = "Salary is required.")
    @DecimalMin(value = "0.01", message = "Salary must be greater than zero.")
    private BigDecimal salary;
}
