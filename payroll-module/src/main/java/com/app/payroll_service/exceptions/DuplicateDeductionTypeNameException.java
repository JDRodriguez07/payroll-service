package com.app.payroll_service.exceptions;

/**
 * Exception thrown when attempting to create or register a deduction type
 * that already exists with the same name.
 */
public class DuplicateDeductionTypeNameException extends RuntimeException {

    /**
     * Constructs a new DuplicateDeductionTypeNameException with a message including
     * the duplicate name.
     *
     * @param name the name of the deduction type that already exists
     */
    public DuplicateDeductionTypeNameException(String name) {
        super("A deduction type with the name '" + name + "' already exists.");
    }
}
