package com.app.payroll_service.exceptions;

public class PayrollNotFoundException extends RuntimeException {
    public PayrollNotFoundException(Long id) {
        super("Payroll not found with id " + id);
    }
}
