package com.app.payroll_service.exceptions;

public class LicenseNotFoundException extends RuntimeException {
    public LicenseNotFoundException(Long id) {
        super("License not found with id " + id);
    }
}
