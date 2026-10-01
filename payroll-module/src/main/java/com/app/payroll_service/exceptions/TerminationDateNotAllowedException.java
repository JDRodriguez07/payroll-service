package com.app.payroll_service.exceptions;

public class TerminationDateNotAllowedException extends RuntimeException {
    public TerminationDateNotAllowedException(Long contractTypeId) {
        super("Termination date is not allowed for contract type ID: " + contractTypeId);
    }
}
