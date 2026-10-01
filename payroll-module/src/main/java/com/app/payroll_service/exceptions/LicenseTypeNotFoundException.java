package com.app.payroll_service.exceptions;

/**
 * Exception thrown when a license type with the specified ID is not found in
 * the system.
 */
public class LicenseTypeNotFoundException extends RuntimeException {

    /**
     * Constructs a new LicenseTypeNotFoundException with a message including the
     * license type ID.
     *
     * @param id the ID of the license type that was not found
     */
    public LicenseTypeNotFoundException(Long id) {
        super("LicenseType not found with id: " + id);
    }
}
