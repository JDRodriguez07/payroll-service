package com.app.payroll_service.exceptions;

public class LicenseTypeNotFoundException extends RuntimeException {
    public LicenseTypeNotFoundException(Long id) {
        super("LicenseType not found with id: " + id);
    }
}
