package com.app.payroll_service.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class DeductionTypeResponseDTO {

    /*
     * 
     */
    private Long deductionTypeId;

    /*
     * 
     */
    private String name;

    /*
     *
     */
    private BigDecimal percentage;

}
