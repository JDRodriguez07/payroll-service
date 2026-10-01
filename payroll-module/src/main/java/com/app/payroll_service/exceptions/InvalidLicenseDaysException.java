package com.app.payroll_service.exceptions;

public class InvalidLicenseDaysException extends RuntimeException {
    public InvalidLicenseDaysException() {
        super("The number of days for a license must be greater than 0.");
    }

    public InvalidLicenseDaysException(String message) {
        super(message);
    }
}
