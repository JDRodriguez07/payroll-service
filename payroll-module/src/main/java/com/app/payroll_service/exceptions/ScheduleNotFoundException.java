package com.app.payroll_service.exceptions;

/**
 * Exception thrown when a schedule with the specified ID is not found in the
 * system.
 */
public class ScheduleNotFoundException extends RuntimeException {

    /**
     * Constructs a new ScheduleNotFoundException with a message including the
     * schedule ID.
     *
     * @param id the ID of the schedule that was not found
     */
    public ScheduleNotFoundException(Long id) {
        super("Schedule not found with id " + id);
    }
}
