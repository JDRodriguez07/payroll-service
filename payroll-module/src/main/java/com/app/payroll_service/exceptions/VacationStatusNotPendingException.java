package com.app.payroll_service.exceptions;

public class VacationStatusNotPendingException extends RuntimeException {

    public VacationStatusNotPendingException(Long id) {
        super("Vacation with id " + id + " is not in PENDING status.");
    }

}
