package com.app.payroll_service.exceptions;

/**
 * Exception thrown when a deduction type with the same name already exists.
 */
public class DuplicateDeductionTypeNameException extends RuntimeException {

    public DuplicateDeductionTypeNameException(String name) {
        super("A deduction type with the name '" + name + "' already exists.");
    }
}
