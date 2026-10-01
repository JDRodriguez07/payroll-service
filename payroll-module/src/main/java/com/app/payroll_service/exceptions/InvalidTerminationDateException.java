package com.app.payroll_service.exceptions;

/**
 * Exception thrown when the termination date of a contract is earlier than the
 * hire date.
 */
public class InvalidTerminationDateException extends RuntimeException {

    /**
     * Constructs a new InvalidTerminationDateException with a default message.
     */
    public InvalidTerminationDateException() {
        super("Termination date cannot be before hire date.");
    }
}
