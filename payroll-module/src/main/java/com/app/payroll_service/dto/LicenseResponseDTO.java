package com.app.payroll_service.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class LicenseResponseDTO {
    private Long licenseId;
    private String licenseTypeDescription;
    private String discountApplies;
    private Long userId;
    private LocalDate startDate;
    private LocalDate endDate;
    private int days;
    private String status;
}
