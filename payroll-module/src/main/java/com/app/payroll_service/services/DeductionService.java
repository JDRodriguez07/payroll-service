package com.app.payroll_service.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.payroll_service.exceptions.DeductionNotFoundException;
import com.app.payroll_service.models.Deduction;
import com.app.payroll_service.repository.DeductionRepository;

@Service
public class DeductionService {

    @Autowired
    private DeductionRepository deductionRepository;

    public List<Deduction> getAllDeductions() {
        return deductionRepository.findAll();
    }

    public Deduction getDeductionById(Long id) {
        return deductionRepository.findById(id)
                .orElseThrow(() -> new DeductionNotFoundException(id));
    }

    public Deduction createDeduction(Deduction deduction) {
        return deductionRepository.save(deduction);
    }

    public void deleteDeduction(Long id) {
        if (!deductionRepository.existsById(id)) {
            throw new DeductionNotFoundException(id);
        }
        deductionRepository.deleteById(id);
    }

    public Deduction updateDeduction(Long id, Deduction deduction) {
        Deduction existingDeduction = deductionRepository.findById(id)
                .orElseThrow(() -> new DeductionNotFoundException(id));

        existingDeduction.setDeductionType(deduction.getDeductionType());
        existingDeduction.setAmount(deduction.getAmount());
    
        return deductionRepository.save(existingDeduction);
    }

}
