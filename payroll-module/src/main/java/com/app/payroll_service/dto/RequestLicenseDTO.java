package com.app.payroll_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class RequestLicenseDTO {

    @NotNull(message = "License type ID is required.")
    private Long licenseTypeId;

    @NotNull(message = "User ID is required.")
    private Long userId;

    @NotNull(message = "Start date is required.")
    private LocalDate startDate;

    @NotNull(message = "End date is required.")
    private LocalDate endDate;
}
