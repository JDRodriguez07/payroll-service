package com.app.payroll_service.exceptions;

public class DeductionTypeNotFoundException extends RuntimeException {
    public DeductionTypeNotFoundException(Long id) {
        super("DeductionType not found with id: " + id);
    }
}
