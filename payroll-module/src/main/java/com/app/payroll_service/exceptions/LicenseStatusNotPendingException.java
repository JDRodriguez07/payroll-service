package com.app.payroll_service.exceptions;

/**
 * Exception thrown when an attempt is made to approve or reject a license
 * that is not in the PENDING status.
 */
public class LicenseStatusNotPendingException extends RuntimeException {

    /**
     * Constructs a new LicenseStatusNotPendingException with a message including
     * the license ID.
     *
     * @param licenseId the ID of the license that is not in PENDING status
     */
    public LicenseStatusNotPendingException(Long licenseId) {
        super("License with ID " + licenseId + " cannot be approved because it is not in PENDING status.");
    }
}
