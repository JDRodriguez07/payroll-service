package com.app.payroll_service.exceptions;

public class VacationStatusNotPendingApprovedException extends RuntimeException {

    public VacationStatusNotPendingApprovedException(Long id) {
        super("Vacation with id " + id + " is not pending or approved status");
    }
    
}
