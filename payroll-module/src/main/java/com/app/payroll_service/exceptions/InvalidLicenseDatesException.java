package com.app.payroll_service.exceptions;

public class InvalidLicenseDatesException extends RuntimeException {
    public InvalidLicenseDatesException() {
        super("Start date cannot be after end date.");
    }

    public InvalidLicenseDatesException(String message) {
        super(message);
    }
}
