package com.app.payroll_service.enums;

public enum ContractStatusEnum {

    ACTIVE("ACTIVO"),
    TERMINATED("TERMINADO"),
    SUSPENDED("SUSPENDIDO");

    private final String value;

    ContractStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
