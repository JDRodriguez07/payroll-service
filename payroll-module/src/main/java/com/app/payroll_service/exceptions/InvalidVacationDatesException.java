package com.app.payroll_service.exceptions;

/**
 * Exception thrown when a vacation's start date is after the end date.
 */
public class InvalidVacationDatesException extends RuntimeException {

    /**
     * Constructs a new InvalidVacationDatesException with a default message.
     */
    public InvalidVacationDatesException() {
        super("Start date cannot be after end date.");
    }
    
}
