package com.app.payroll_service.exceptions;

public class ContractNotFoundException extends RuntimeException {
    public ContractNotFoundException(Long id) {
        super("Contract not found with id " + id);
    }
}
