package com.app.payroll_service.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Data Transfer Object (DTO) for requesting vacation leave.
 */
@Data
public class RequestVacationDTO {

    /**
     * ID of the user requesting the vacation.
     * Cannot be null.
     */
    @NotNull(message = "User ID is required.")
    private Long userId;

    /**
     * Start date of the vacation period.
     * Cannot be null.
     */
    @NotNull(message = "Start date is required.")
    private LocalDate startDate;

    /**
     * End date of the vacation period.
     * Cannot be null.
     */
    @NotNull(message = "End date is required.")
    private LocalDate endDate;
    
}
