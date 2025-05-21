package com.app.payroll_service.dto;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for returning license type details.
 */
@Data
public class LicenseTypeResponseDTO {

    /**
     * Unique identifier of the license type.
     */
    private Long licenseTypeId;

    /**
     * Description of the license type (e.g., medical leave, study leave).
     */
    private String description;

    /**
     * Indicates whether the license type involves a discount or deduction.
     */
    private boolean isDiscount;
}
