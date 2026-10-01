package com.app.payroll_service.exceptions;

/**
 * Exception thrown when the contract dates are invalid,
 * such as when the termination date is before the hire date.
 */
public class InvalidContractDatesException extends RuntimeException {

    /**
     * Constructs an InvalidContractDatesException with a default message.
     */
    public InvalidContractDatesException() {
        super("Termination date cannot be before hire date.");
    }

    /**
     * Constructs an InvalidContractDatesException with a custom message.
     *
     * @param message the detailed error message
     */
    public InvalidContractDatesException(String message) {
        super(message);
    }
}
