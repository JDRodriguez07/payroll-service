package com.app.payroll_service.exceptions;

public class ContractAlreadyTerminatedException extends RuntimeException {
    public ContractAlreadyTerminatedException(Long contractId) {
        super("Contract with ID " + contractId + " is already terminated or inactive.");
    }
}
