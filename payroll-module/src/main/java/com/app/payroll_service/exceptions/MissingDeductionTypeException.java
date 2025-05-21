package com.app.payroll_service.exceptions;

/**
 * Exception thrown when a deduction is missing its associated deduction type.
 */
public class MissingDeductionTypeException extends RuntimeException {

    /**
     * Constructs a new MissingDeductionTypeException with a default message.
     */
    public MissingDeductionTypeException() {
        super("Deduction type is required.");
    }
}
