package com.app.payroll_service.exceptions;

public class LicenseStatusNotPendingException extends RuntimeException {
    public LicenseStatusNotPendingException(Long licenseId) {
        super("License with ID " + licenseId + " cannot be approved because it is not in PENDING status.");
    }
}
