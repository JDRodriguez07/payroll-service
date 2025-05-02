package com.app.payroll_service.exceptions;

/**
 * Exception thrown when a vacation record with the specified ID is not found in
 * the system.
 */
public class VacationNotFoundException extends RuntimeException {

    /**
     * Constructs a new VacationNotFoundException with a message including the
     * vacation ID.
     *
     * @param id the ID of the vacation that was not found
     */
    public VacationNotFoundException(Long id) {
        super("Vacation not found with id: " + id);
    }
}
