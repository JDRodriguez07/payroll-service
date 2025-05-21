package com.app.payroll_service.enums;

/**
 * Enumeration representing the possible statuses of a contract.
 * The values are displayed in Spanish as the application is intended for a Spanish-speaking audience.
 */
public enum ContractStatusEnum {

    ACTIVE("ACTIVO"),
    TERMINATED("TERMINADO"),
    SUSPENDED("SUSPENDIDO");

    private final String value;

    ContractStatusEnum(String value) {
        this.value = value;
    }

    /**
     * Returns the display value of the contract status.
     *
     * @return the status value in Spanish
     */
    public String getValue() {
        return value;
    }
}
