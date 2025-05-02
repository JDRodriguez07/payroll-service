package com.app.payroll_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;

/**
 * Global exception handler for the application.
 * Intercepts and formats exceptions into structured HTTP responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // --- Not Found Handlers ---

    @ExceptionHandler(ContractNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleContractNotFound(ContractNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ContractTypeNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleContractTypeNotFound(ContractTypeNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(PayrollNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlePayrollNotFound(PayrollNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ScheduleNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleScheduleNotFound(ScheduleNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(LicenseNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleLicenseNotFound(LicenseNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(DeductionNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleDeductionNotFound(DeductionNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(DeductionTypeNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleDeductionTypeNotFound(DeductionTypeNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(LicenseTypeNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleLicenseTypeNotFound(LicenseTypeNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(PayrollDeductionsNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlePayrollDeductionsNotFound(PayrollDeductionsNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(VacationNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleVacationNotFound(VacationNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // --- Bad Request Handlers ---

    @ExceptionHandler(InvalidLicenseDatesException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidLicenseDates(InvalidLicenseDatesException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(InvalidLicenseDaysException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidLicenseDays(InvalidLicenseDaysException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(InvalidVacationDatesException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidVacationDates(InvalidVacationDatesException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(InvalidVacationDaysException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidVacationDays(InvalidVacationDaysException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(DuplicateDeductionTypeNameException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicateDeductionType(DuplicateDeductionTypeNameException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(InvalidDeductionAmountException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidDeductionAmount(InvalidDeductionAmountException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(MissingDeductionTypeException.class)
    public ResponseEntity<Map<String, Object>> handleMissingDeductionType(MissingDeductionTypeException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(InvalidContractSalaryException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidSalary(InvalidContractSalaryException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(MissingHireDateException.class)
    public ResponseEntity<Map<String, Object>> handleMissingHireDate(MissingHireDateException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(MissingTerminationDateException.class)
    public ResponseEntity<Map<String, Object>> handleMissingTerminationDate(MissingTerminationDateException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(InvalidTerminationDateException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidTerminationDate(InvalidTerminationDateException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(TerminationDateNotAllowedException.class)
    public ResponseEntity<Map<String, Object>> handleTerminationDateNotAllowed(TerminationDateNotAllowedException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(ContractAlreadyTerminatedException.class)
    public ResponseEntity<Map<String, Object>> handleContractAlreadyTerminated(ContractAlreadyTerminatedException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(ContractAlreadyFinalizedException.class)
    public ResponseEntity<Map<String, Object>> handleContractAlreadyFinalized(ContractAlreadyFinalizedException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(LicenseStatusNotPendingException.class)
    public ResponseEntity<Map<String, Object>> handleLicenseAlreadyProcessed(LicenseStatusNotPendingException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(LicenseCanceledTerminatedRejectedException.class)
    public ResponseEntity<Map<String, Object>> handleLicenseAlreadyCanceledOrTerminated(
            LicenseCanceledTerminatedRejectedException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(VacationStatusNotPendingApprovedException.class)
    public ResponseEntity<Map<String, Object>> handleVacationAlreadyProcessed(
            VacationStatusNotPendingApprovedException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    /**
     * Handles validation errors for invalid method arguments.
     *
     * @param ex the exception thrown by the validator
     * @return structured error response
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());

        // Extracts the first validation message
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getDefaultMessage())
                .findFirst()
                .orElse("Validation error");

        body.put("message", message);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * Builds a standardized error response body.
     *
     * @param status  the HTTP status to return
     * @param message the error message to include
     * @return a ResponseEntity containing the structured error
     */
    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        return new ResponseEntity<>(body, status);
    }
    
}
