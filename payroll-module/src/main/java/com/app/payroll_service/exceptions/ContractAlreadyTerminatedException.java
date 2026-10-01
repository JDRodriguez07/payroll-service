package com.app.payroll_service.exceptions;

/**
 * Exception thrown when attempting to terminate a contract
 * that is already in a terminated or inactive state.
 */
public class ContractAlreadyTerminatedException extends RuntimeException {

    /**
     * Constructs a new ContractAlreadyTerminatedException with a message
     * including the contract ID.
     *
     * @param contractId the ID of the contract that is already terminated or
     *                   inactive
     */
    public ContractAlreadyTerminatedException(Long contractId) {
        super("Contract with ID " + contractId + " is already terminated or inactive.");
    }
}
