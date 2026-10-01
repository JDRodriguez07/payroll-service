package com.app.payroll_service.exceptions;

/**
 * Exception thrown when a termination date is required for a contract type but
 * is not provided.
 */
public class MissingTerminationDateException extends RuntimeException {

    /**
     * Constructs a new MissingTerminationDateException with a message including the
     * contract type ID.
     *
     * @param contractTypeId the ID of the contract type that requires a termination
     *                       date
     */
    public MissingTerminationDateException(Long contractTypeId) {
        super("Contract type with ID " + contractTypeId + " requires a termination date.");
    }
    
}
