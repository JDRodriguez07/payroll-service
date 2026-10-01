package com.app.payroll_service.exceptions;

public class VacationNotFoundException extends RuntimeException {
    public VacationNotFoundException(Long id) {
        super("Vacation not found with id: " + id);
    }
}
