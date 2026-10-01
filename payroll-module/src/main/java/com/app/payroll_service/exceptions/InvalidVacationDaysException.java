package com.app.payroll_service.exceptions;

/**
 * Exception thrown when the number of vacation days taken is zero or negative.
 */
public class InvalidVacationDaysException extends RuntimeException {

    public InvalidVacationDaysException() {
        super("Taken days must be greater than 0.");
    }

    public InvalidVacationDaysException(String message) {
        super(message);
    }
}
