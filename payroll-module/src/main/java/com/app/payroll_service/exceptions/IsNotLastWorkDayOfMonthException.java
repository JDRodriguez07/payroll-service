package com.app.payroll_service.exceptions;

public class IsNotLastWorkDayOfMonthException extends RuntimeException {

    public IsNotLastWorkDayOfMonthException() {
        super("Hoy no es el último día de mes para realizar esta operación.");
    }

}
