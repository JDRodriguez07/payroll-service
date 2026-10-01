package com.app.payroll_service.exceptions;

public class InvalidContractSalaryException extends RuntimeException {
    public InvalidContractSalaryException() {
        super("Salary must be greater than zero.");
    }
}
