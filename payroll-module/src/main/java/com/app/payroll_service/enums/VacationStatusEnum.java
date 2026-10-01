package com.app.payroll_service.enums;

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

    public String getValue() {
        return value;
    }
    
}
