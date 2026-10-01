package com.app.payroll_service.dto;

import java.time.LocalDate;
import lombok.Data;

/**
 * Data Transfer Object (DTO) for returning vacation request details.
 */
@Data
public class VacationResponseDTO {

    /**
     * Unique identifier of the vacation request.
     */
    private Long vacationId;

    /**
     * ID of the user who requested the vacation.
     */
    private Long userId;

    /**
     * Start date of the vacation period.
     */
    private LocalDate startDate;

    /**
     * End date of the vacation period.
     */
    private LocalDate endDate;

    /**
     * Number of vacation days taken.
     */
    private int takenDays;

    /**
     * Current status of the vacation request (e.g., PENDING, APPROVED, REJECTED).
     */
    private String status;

}
