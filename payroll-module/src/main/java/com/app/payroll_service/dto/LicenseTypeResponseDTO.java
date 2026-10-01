package com.app.payroll_service.dto;

import lombok.Data;

@Data
public class LicenseTypeResponseDTO {
    
    Long licenseTypeId;
    String description;
    boolean isDiscount;

}
