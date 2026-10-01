package com.app.payroll_service.exceptions;

/**
 * Exception thrown when a contract is assigned an invalid salary,
 * such as a value less than or equal to zero.
 */
public class InvalidContractSalaryException extends RuntimeException {

    /**
     * Constructs a new InvalidContractSalaryException with a default message.
     */
    public InvalidContractSalaryException() {
        super("Salary must be greater than zero.");
    }
}
