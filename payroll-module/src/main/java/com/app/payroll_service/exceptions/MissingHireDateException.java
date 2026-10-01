package com.app.payroll_service.exceptions;

/**
 * Exception thrown when a required hire date is missing during contract
 * creation or update.
 */
public class MissingHireDateException extends RuntimeException {

    /**
     * Constructs a new MissingHireDateException with a default message.
     */
    public MissingHireDateException() {
        super("Hire date is required.");
    }
    
}
