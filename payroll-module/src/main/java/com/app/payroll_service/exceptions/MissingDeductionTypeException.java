package com.app.payroll_service.exceptions;

/**
 * Exception thrown when a deduction is missing its deduction type.
 */
public class MissingDeductionTypeException extends RuntimeException {

    public MissingDeductionTypeException() {
        super("Deduction type is required.");
    }
}
