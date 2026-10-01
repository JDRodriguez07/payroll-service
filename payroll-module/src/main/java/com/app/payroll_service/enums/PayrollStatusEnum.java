package com.app.payroll_service.enums;

public enum PayrollStatusEnum {

    PENDING("PENDIENTE"),
    PAID("PAGADO");
    
    private final String value;

    PayrollStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
