package com.app.payroll_service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class ContractResponseDTO {

    private Long contractId;
    private String contractTypeName;
    private LocalTime startTime;
    private LocalTime endTime;
    private String status;
    private LocalDate hireDate;
    private LocalDate terminationDate;
    private BigDecimal salary;

}
