package com.app.payroll_service.exceptions;

/**
 * Exception thrown when attempting to cancel a license that is already in a
 * CANCELED, TERMINATED, or REJECTED state.
 */
public class LicenseCanceledTerminatedRejectedException extends RuntimeException {

    /**
     * Constructs a new LicenseCanceledTerminatedRejectedException with a message
     * that includes the license ID.
     *
     * @param licenseId the ID of the license that cannot be canceled
     */
    public LicenseCanceledTerminatedRejectedException(Long licenseId) {
        super("License with ID " + licenseId
                + " cannot be canceled because it is already in CANCELED, TERMINATED, or REJECTED status.");
    }
}
