package com.app.payroll_service.exceptions;

public class LicenseNotPendingApprovedStatusException extends RuntimeException {

    public LicenseNotPendingApprovedStatusException(Long id) {
        super("License with ID " + id + " is not in a pending or approved status.");
    }

}
