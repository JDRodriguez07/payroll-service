package com.app.payroll_service.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RequestVacationDTO {

    @NotNull(message = "User ID is required.")
    private Long userId;

    @NotNull(message = "Start date is required.")
    private LocalDate startDate;

    @NotNull(message = "End date is required.")
    private LocalDate endDate;
    
}
