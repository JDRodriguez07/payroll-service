package com.app.payroll_service.exceptions;

/**
 * Exception thrown when a deduction type with the specified ID is not found in
 * the system.
 */
public class DeductionTypeNotFoundException extends RuntimeException {

    /**
     * Constructs a new DeductionTypeNotFoundException with a message including the
     * deduction type ID.
     *
     * @param id the ID of the deduction type that was not found
     */
    public DeductionTypeNotFoundException(Long id) {
        super("DeductionType not found with id: " + id);
    }
}
