package com.app.payroll_service.exceptions;

public class MissingHireDateException extends RuntimeException {
    public MissingHireDateException() {
        super("Hire date is required.");
    }
}
