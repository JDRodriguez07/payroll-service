package com.app.payroll_service.exceptions;

public class PayrollDeductionsNotFoundException extends RuntimeException {
    public PayrollDeductionsNotFoundException(Long id) {
        super("PayrollDeduction not found with id: " + id);
    }
}
