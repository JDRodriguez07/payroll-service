package com.app.payroll_service.exceptions;

/**
 * Exception thrown when a payroll record with the specified ID is not found in
 * the system.
 */
public class PayrollNotFoundException extends RuntimeException {

    /**
     * Constructs a new PayrollNotFoundException with a message including the
     * payroll ID.
     *
     * @param id the ID of the payroll record that was not found
     */
    public PayrollNotFoundException(Long id) {
        super("Payroll not found with id " + id);
    }
    
}
