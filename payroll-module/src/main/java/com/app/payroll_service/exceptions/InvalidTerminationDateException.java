package com.app.payroll_service.exceptions;

public class InvalidTerminationDateException extends RuntimeException {
    public InvalidTerminationDateException() {
        super("Termination date cannot be before hire date.");
    }
}
