package com.app.payroll_service.exceptions;

public class LicenseCanceledTerminatedRejectedException extends RuntimeException {
    public LicenseCanceledTerminatedRejectedException(Long licenseId) {
        super("License with ID " + licenseId
                + " cannot be canceled because it is already CANCELED, TERMINATED or REJECTED status.");
    }
}
