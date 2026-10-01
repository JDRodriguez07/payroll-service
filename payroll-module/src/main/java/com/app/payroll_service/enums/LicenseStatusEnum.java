package com.app.payroll_service.enums;

/**
 * Enumeration representing the possible statuses of a license.
 * The values are displayed in Spanish, as the application targets a
 * Spanish-speaking audience.
 */
public enum LicenseStatusEnum {

    PENDING("PENDIENTE"),
    CANCELED("CANCELADA"),
    APPROVED("APROBADA"),
    REJECTED("RECHAZADA"),
    TERMINATED("TERMINADA");

    private final String value;

    LicenseStatusEnum(String value) {
        this.value = value;
    }

    /**
     * Returns the display value of the license status.
     *
     * @return the status value in Spanish
     */
    public String getValue() {
        return value;
    }
    
}
