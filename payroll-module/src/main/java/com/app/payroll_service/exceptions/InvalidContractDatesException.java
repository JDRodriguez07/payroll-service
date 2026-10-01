package com.app.payroll_service.exceptions;

public class InvalidContractDatesException extends RuntimeException {

    public InvalidContractDatesException() {
        super("Termination date cannot be before hire date.");
    }

    public InvalidContractDatesException(String message) {
        super(message);
    }
}