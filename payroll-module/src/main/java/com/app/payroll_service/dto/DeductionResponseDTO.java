package com.app.payroll_service.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class DeductionResponseDTO {

    private Long deductionId;
    private String name;
    private BigDecimal amount;

}
