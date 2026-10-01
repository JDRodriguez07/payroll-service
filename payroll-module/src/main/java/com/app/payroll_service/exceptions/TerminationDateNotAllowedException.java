package com.app.payroll_service.exceptions;

/**
 * Exception thrown when a termination date is provided for a contract type that
 * does not allow it.
 */
public class TerminationDateNotAllowedException extends RuntimeException {

    /**
     * Constructs a new TerminationDateNotAllowedException with a message including
     * the contract type ID.
     *
     * @param contractTypeId the ID of the contract type that does not allow a
     *                       termination date
     */
    public TerminationDateNotAllowedException(Long contractTypeId) {
        super("Termination date is not allowed for contract type ID: " + contractTypeId);
    }
    
}
