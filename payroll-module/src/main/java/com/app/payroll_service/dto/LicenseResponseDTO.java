package com.app.payroll_service.dto;

import java.time.LocalDate;
import lombok.Data;

/**
 * Data Transfer Object (DTO) for returning license details.
 */
@Data
public class LicenseResponseDTO {

    /**
     * Unique identifier of the license.
     */
    private Long licenseId;

    /**
     * Description of the license type (e.g., medical, personal leave).
     */
    private String licenseTypeDescription;

    /**
     * Indicates whether a discount or deduction applies due to the license.
     */
    private String discountApplies;

    /**
     * ID of the user associated with the license.
     */
    private Long userId;

    /**
     * Start date of the license period.
     */
    private LocalDate startDate;

    /**
     * End date of the license period.
     */
    private LocalDate endDate;

    /**
     * Total number of days requested or approved for the license.
     */
    private int days;

    /**
     * Current status of the license (e.g., PENDING, APPROVED, REJECTED).
     */
    private String status;
}
