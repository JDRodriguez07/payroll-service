package com.app.payroll_service.exceptions;

public class ContractAlreadyFinalizedException extends RuntimeException {
    public ContractAlreadyFinalizedException(Long contractId) {
        super("The contract with ID " + contractId
                + " already has a termination date and cannot be manually terminated.");
    }
}
