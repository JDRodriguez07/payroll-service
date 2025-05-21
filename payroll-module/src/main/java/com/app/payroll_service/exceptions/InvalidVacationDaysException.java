package com.app.payroll_service.exceptions;

/**
 * Exception thrown when the number of vacation days taken is invalid,
 * such as being zero, negative, or not equal to the required number (e.g., 15).
 */
public class InvalidVacationDaysException extends RuntimeException {

    /**
     * Constructs a new InvalidVacationDaysException with the default message.
     */
    public InvalidVacationDaysException() {
        super("Taken days must be 15");
    }

    /**
     * Constructs a new InvalidVacationDaysException with a custom message.
     *
     * @param message the detailed error message
     */
    public InvalidVacationDaysException(String message) {
        super(message);
    }
}
