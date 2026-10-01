package com.app.payroll_service.exceptions;

public class LicenseCanceledOrTerminatedException extends RuntimeException {
    public LicenseCanceledOrTerminatedException(Long licenseId) {
        super("License with ID " + licenseId
                + " cannot be canceled because it is already CANCELED or TERMINATED status.");
    }
}
