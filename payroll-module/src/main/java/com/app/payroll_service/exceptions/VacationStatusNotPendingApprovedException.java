package com.app.payroll_service.exceptions;

/**
 * Exception thrown when attempting to modify a vacation request
 * that is not in PENDING or APPROVED status.
 */
public class VacationStatusNotPendingApprovedException extends RuntimeException {

    /**
     * Constructs a new VacationStatusNotPendingApprovedException with a message
     * including the vacation ID.
     *
     * @param id the ID of the vacation that is not in a valid status for the
     *           operation
     */
    public VacationStatusNotPendingApprovedException(Long id) {
        super("Vacation with id " + id + " is not in PENDING or APPROVED status.");
    }
    
}
