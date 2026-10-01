package com.app.payroll_service.exceptions;

/**
 * Exception thrown when attempting to manually terminate a contract
 * that already has a termination date set (i.e., it is considered finalized).
 */
public class ContractAlreadyFinalizedException extends RuntimeException {

    /**
     * Constructs a new ContractAlreadyFinalizedException with a message
     * including the contract ID.
     *
     * @param contractId the ID of the contract that is already finalized
     */
    public ContractAlreadyFinalizedException(Long contractId) {
        super("The contract with ID " + contractId
                + " already has a termination date and cannot be manually terminated.");
    }

}
