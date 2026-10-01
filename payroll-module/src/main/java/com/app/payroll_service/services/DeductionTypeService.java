package com.app.payroll_service.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.payroll_service.exceptions.DeductionTypeNotFoundException;
import com.app.payroll_service.models.DeductionType;
import com.app.payroll_service.repository.DeductionTypeRepository;

@Service
public class DeductionTypeService {

    @Autowired
    private DeductionTypeRepository deductionTypeRepository;

    public List<DeductionType> getAllDeductionTypes() {
        return deductionTypeRepository.findAll();
    }

    public DeductionType getDeductionTypeById(Long id) {
        return deductionTypeRepository.findById(id)
                .orElseThrow(() -> new DeductionTypeNotFoundException(id));
    }

    public DeductionType createDeductionType(DeductionType deductionType) {
        return deductionTypeRepository.save(deductionType);
    }

    public void deleteDeductionType(Long id) {
        if (!deductionTypeRepository.existsById(id)) {
            throw new DeductionTypeNotFoundException(id);
        }
        deductionTypeRepository.deleteById(id);
    }

    public DeductionType updateDeductionType(Long id, DeductionType updated) {
        DeductionType existingDeductionType = deductionTypeRepository.findById(id)
                .orElseThrow(() -> new DeductionTypeNotFoundException(id));

        existingDeductionType.setName(updated.getName());
        existingDeductionType.setPercentage(updated.getPercentage());

        return deductionTypeRepository.save(existingDeductionType);
    }
}
