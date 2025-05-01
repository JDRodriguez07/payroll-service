package com.app.payroll_service.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class VacationResponseDTO {

    private Long vacationId;
    private Long userId;
    private LocalDate startDate;
    private LocalDate endDate;
    private int takenDays;
    private String status;
    
}
