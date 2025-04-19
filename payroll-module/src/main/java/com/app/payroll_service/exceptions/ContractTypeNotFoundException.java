package com.app.payroll_service.exceptions;

public class ContractTypeNotFoundException extends RuntimeException {
    public ContractTypeNotFoundException(Long id) {
        super("ContractType not found with id " + id);
    }
    
}
