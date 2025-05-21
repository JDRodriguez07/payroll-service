package com.app.payroll_service.exceptions;

/**
 * Exception thrown when the number of days assigned to a license is invalid,
 * such as zero or a negative number.
 */
public class InvalidLicenseDaysException extends RuntimeException {

    /**
     * Constructs a new InvalidLicenseDaysException with a default message.
     */
    public InvalidLicenseDaysException() {
        super("The number of days for a license must be greater than 0.");
    }

    /**
     * Constructs a new InvalidLicenseDaysException with a custom message.
     *
     * @param message the detailed error message
     */
    public InvalidLicenseDaysException(String message) {
        super(message);
    }
}
