package com.app.payroll_service.exceptions;

/**
 * Exception thrown when a contract type with the specified ID is not found in
 * the system.
 */
public class ContractTypeNotFoundException extends RuntimeException {

    /**
     * Constructs a new ContractTypeNotFoundException with a message including the
     * contract type ID.
     *
     * @param id the ID of the contract type that was not found
     */
    public ContractTypeNotFoundException(Long id) {
        super("ContractType not found with id " + id);
    }
}
