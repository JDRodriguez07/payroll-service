package com.app.payroll_service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

@Data
public class UpdateContractDTO {

    private Long scheduleId;

    private LocalDate hireDate;

    private LocalDate terminationDate;

    @DecimalMin(value = "0.01", message = "Salary must be greater than zero.")
    private BigDecimal salary;
}

