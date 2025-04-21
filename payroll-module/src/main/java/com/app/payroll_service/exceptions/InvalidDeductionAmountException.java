package com.app.payroll_service.exceptions;

/**
 * Exception thrown when a deduction amount is less than or equal to zero.
 */
public class InvalidDeductionAmountException extends RuntimeException {
    public InvalidDeductionAmountException() {
        super("Deduction amount must be greater than 0.");
    }
}
