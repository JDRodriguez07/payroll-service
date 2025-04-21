package com.app.payroll_service.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.payroll_service.exceptions.DeductionNotFoundException;
import com.app.payroll_service.exceptions.InvalidDeductionAmountException;
import com.app.payroll_service.exceptions.MissingDeductionTypeException;
import com.app.payroll_service.models.Deduction;
import com.app.payroll_service.repository.DeductionRepository;

@Service
public class DeductionService {

    @Autowired
    private DeductionRepository deductionRepository;

    /**
     * Retrieves all deductions from the database.
     *
     * @return List of all deductions
     */
    public List<Deduction> getAllDeductions() {
        return deductionRepository.findAll();
    }

    /**
     * Retrieves a deduction by its ID.
     *
     * @param id Deduction ID
     * @return Deduction
     * @throws DeductionNotFoundException if not found
     */
    public Deduction getDeductionById(Long id) {
        return deductionRepository.findById(id)
                .orElseThrow(() -> new DeductionNotFoundException(id));
    }

    /**
     * Creates a new deduction. Validates the amount and type.
     *
     * @param deduction Deduction to create
     * @return Created deduction
     * @throws MissingDeductionTypeException   if type is null
     * @throws InvalidDeductionAmountException if amount is null or non-positive
     */
    public Deduction createDeduction(Deduction deduction) {
        validateDeductionType(deduction);
        validateAmount(deduction.getAmount());
        return deductionRepository.save(deduction);
    }

    /**
     * Updates an existing deduction. Validates the amount and type.
     *
     * @param id        Deduction ID
     * @param deduction Updated deduction data
     * @return Updated deduction
     * @throws DeductionNotFoundException      if not found
     * @throws MissingDeductionTypeException   if type is null
     * @throws InvalidDeductionAmountException if amount is invalid
     */
    public Deduction updateDeduction(Long id, Deduction deduction) {
        Deduction existingDeduction = deductionRepository.findById(id)
                .orElseThrow(() -> new DeductionNotFoundException(id));

        validateDeductionType(deduction);
        validateAmount(deduction.getAmount());

        existingDeduction.setDeductionType(deduction.getDeductionType());
        existingDeduction.setAmount(deduction.getAmount());

        return deductionRepository.save(existingDeduction);
    }

    /**
     * Deletes a deduction by ID.
     *
     * @param id Deduction ID
     * @throws DeductionNotFoundException if not found
     */
    public void deleteDeduction(Long id) {
        if (!deductionRepository.existsById(id)) {
            throw new DeductionNotFoundException(id);
        }
        deductionRepository.deleteById(id);
    }

    /**
     * Validates that the deduction amount is not null and greater than zero.
     *
     * @param amount Deduction amount
     * @throws InvalidDeductionAmountException if invalid
     */
    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidDeductionAmountException();
        }
    }

    /**
     * Validates that the deduction type is not null.
     *
     * @param deduction Deduction to validate
     * @throws MissingDeductionTypeException if type is null
     */
    private void validateDeductionType(Deduction deduction) {
        if (deduction.getDeductionType() == null) {
            throw new MissingDeductionTypeException();
        }
    }
}
