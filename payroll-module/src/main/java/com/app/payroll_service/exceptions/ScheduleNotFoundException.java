package com.app.payroll_service.exceptions;

public class ScheduleNotFoundException extends RuntimeException {
    public ScheduleNotFoundException(Long id) {
        super("Schedule not found with id " + id);
    }
}
