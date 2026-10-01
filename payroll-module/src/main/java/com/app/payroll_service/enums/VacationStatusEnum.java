package com.app.payroll_service.enums;

/**
 * Enumeration representing the possible statuses of a vacation request.
 * The values are in Spanish to align with the application's target audience.
 */
public enum VacationStatusEnum {

    PENDING("PENDIENTE"),
    CANCELED("CANCELADA"),
    APPROVED("APROBADA"),
    REJECTED("RECHAZADA"),
    TERMINATED("TERMINADA");

    private final String value;

    VacationStatusEnum(String value) {
        this.value = value;
    }

    /**
     * Returns the display value of the vacation status.
     *
     * @return the status value in Spanish
     */
    public String getValue() {
        return value;
    }
    
}
