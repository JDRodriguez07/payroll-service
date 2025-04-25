package com.app.payroll_service.exceptions;

public class MissingTerminationDateException extends RuntimeException {

    public MissingTerminationDateException(Long contractTypeId) {
        super("Contract type with ID " + contractTypeId + " requires a termination date.");
    }
}
