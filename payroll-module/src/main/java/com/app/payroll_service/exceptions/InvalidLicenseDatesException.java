package com.app.payroll_service.exceptions;

/**
 * Exception thrown when the provided license dates are invalid,
 * such as when the start date is after the end date.
 */
public class InvalidLicenseDatesException extends RuntimeException {

    /**
     * Constructs a new InvalidLicenseDatesException with a default message.
     */
    public InvalidLicenseDatesException() {
        super("Start date cannot be after end date.");
    }

    /**
     * Constructs a new InvalidLicenseDatesException with a custom message.
     *
     * @param message the detailed error message
     */
    public InvalidLicenseDatesException(String message) {
        super(message);
    }
}
