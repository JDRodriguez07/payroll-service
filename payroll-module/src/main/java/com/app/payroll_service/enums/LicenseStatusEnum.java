package com.app.payroll_service.enums;

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

    public String getValue() {
        return value;
    }
}
