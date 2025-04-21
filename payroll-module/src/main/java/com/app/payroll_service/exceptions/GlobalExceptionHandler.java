package com.app.payroll_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ContractNotFoundException.class)
    public ResponseEntity<String> handleContractNotFound(ContractNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ContractTypeNotFoundException.class)
    public ResponseEntity<String> handleContractTypeNotFound(ContractTypeNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(PayrollNotFoundException.class)
    public ResponseEntity<String> handlePayrollNotFound(PayrollNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ScheduleNotFoundException.class)
    public ResponseEntity<String> handleScheduleNotFound(ScheduleNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(LicenseNotFoundException.class)
    public ResponseEntity<String> handleLicenseNotFound(LicenseNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(DeductionNotFoundException.class)
    public ResponseEntity<String> handleDeductionNotFound(DeductionNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(DeductionTypeNotFoundException.class)
    public ResponseEntity<String> handleDeductionTypeNotFound(DeductionTypeNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(LicenseTypeNotFoundException.class)
    public ResponseEntity<String> handleLicenseTypeNotFound(LicenseTypeNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(PayrollDeductionsNotFoundException.class)
    public ResponseEntity<String> handlePayrollDeductionsNotFound(PayrollDeductionsNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(VacationNotFoundException.class)
    public ResponseEntity<String> handleVacationNotFound(VacationNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidLicenseDatesException.class)
    public ResponseEntity<String> handleInvalidLicenseDates(InvalidLicenseDatesException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidLicenseDaysException.class)
    public ResponseEntity<String> handleInvalidLicenseDays(InvalidLicenseDaysException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidVacationDatesException.class)
    public ResponseEntity<String> handleInvalidVacationDates(InvalidVacationDatesException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidVacationDaysException.class)
    public ResponseEntity<String> handleInvalidVacationDays(InvalidVacationDaysException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(DuplicateDeductionTypeNameException.class)
    public ResponseEntity<String> handleDuplicateDeductionType(DuplicateDeductionTypeNameException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidDeductionAmountException.class)
    public ResponseEntity<String> handleInvalidDeductionAmount(InvalidDeductionAmountException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(MissingDeductionTypeException.class)
    public ResponseEntity<String> handleMissingDeductionType(MissingDeductionTypeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

}
