package com.app.payroll_service.exceptions;

/**
 * Exception thrown when payroll deductions with the specified ID are not found
 * in the system.
 */
public class PayrollDeductionsNotFoundException extends RuntimeException {

    /**
     * Constructs a new PayrollDeductionsNotFoundException with a message including
     * the ID.
     *
     * @param id the ID of the payroll deduction record that was not found
     */
    public PayrollDeductionsNotFoundException(Long id) {
        super("PayrollDeduction not found with id: " + id);
    }
    
}
