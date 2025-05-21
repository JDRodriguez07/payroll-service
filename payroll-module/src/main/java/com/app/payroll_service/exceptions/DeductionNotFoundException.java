package com.app.payroll_service.exceptions;

/**
 * Exception thrown when a deduction with the specified ID is not found in the
 * system.
 */
public class DeductionNotFoundException extends RuntimeException {

    /**
     * Constructs a new DeductionNotFoundException with a message including the
     * deduction ID.
     *
     * @param id the ID of the deduction that was not found
     */
    public DeductionNotFoundException(Long id) {
        super("Deduction not found with id " + id);
    }
    
}
