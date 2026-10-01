package com.app.payroll_service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class PayrollResponseDTO {

    private Long payrollId;
    private Long userId;
    private int paidDays;
    private LocalDate initialPeriod;
    private LocalDate finalPeriod;
    private BigDecimal grossSalary;
    private BigDecimal netSalary;
    private String status;
    private List<DeductionResponseDTO> payrollDeductions;

}
