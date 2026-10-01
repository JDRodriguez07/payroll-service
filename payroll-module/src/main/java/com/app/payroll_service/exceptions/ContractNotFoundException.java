package com.app.payroll_service.exceptions;

/**
 * Exception thrown when a contract with the specified ID is not found in the
 * system.
 */
public class ContractNotFoundException extends RuntimeException {

    /**
     * Constructs a new ContractNotFoundException with a message including the
     * contract ID.
     *
     * @param id the ID of the contract that was not found
     */
    public ContractNotFoundException(Long id) {
        super("Contract not found with id " + id);
    }
}
