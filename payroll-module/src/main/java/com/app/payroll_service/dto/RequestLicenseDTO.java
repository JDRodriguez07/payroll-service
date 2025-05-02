package com.app.payroll_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) for requesting a license.
 */
@Data
public class RequestLicenseDTO {

    /**
     * ID of the license type being requested.
     * Cannot be null.
     */
    @NotNull(message = "License type ID is required.")
    private Long licenseTypeId;

    /**
     * ID of the user requesting the license.
     * Cannot be null.
     */
    @NotNull(message = "User ID is required.")
    private Long userId;

    /**
     * Start date of the requested license period.
     * Cannot be null.
     */
    @NotNull(message = "Start date is required.")
    private LocalDate startDate;

    /**
     * End date of the requested license period.
     * Cannot be null.
     */
    @NotNull(message = "End date is required.")
    private LocalDate endDate;
}
