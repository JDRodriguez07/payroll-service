package com.app.payroll_service.exceptions;

/**
 * Exception thrown when a license with the specified ID is not found in the
 * system.
 */
public class LicenseNotFoundException extends RuntimeException {

    /**
     * Constructs a new LicenseNotFoundException with a message including the
     * license ID.
     *
     * @param id the ID of the license that was not found
     */
    public LicenseNotFoundException(Long id) {
        super("License not found with id " + id);
    }
}
