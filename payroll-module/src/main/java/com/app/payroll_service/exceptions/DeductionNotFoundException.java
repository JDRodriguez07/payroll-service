package com.app.payroll_service.exceptions;

public class DeductionNotFoundException extends RuntimeException{
    public DeductionNotFoundException(Long id) {
        super("Deduction not found with id " + id);
    }
}
