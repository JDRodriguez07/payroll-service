package com.app.payroll_service.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.payroll_service.exceptions.DeductionTypeNotFoundException;
import com.app.payroll_service.exceptions.DuplicateDeductionTypeNameException;
import com.app.payroll_service.models.DeductionType;
import com.app.payroll_service.repository.DeductionTypeRepository;

@Service
public class DeductionTypeService {

    @Autowired
    private DeductionTypeRepository deductionTypeRepository;

    /**
     * Retrieves all deduction types from the database.
     *
     * @return List of all deduction types
     */
    public List<DeductionType> getAllDeductionTypes() {
        return deductionTypeRepository.findAll();
    }

    /**
     * Retrieves a deduction type by its ID.
     *
     * @param id Deduction type ID
     * @return DeductionType
     * @throws DeductionTypeNotFoundException if not found
     */
    public DeductionType getDeductionTypeById(Long id) {
        return deductionTypeRepository.findById(id)
                .orElseThrow(() -> new DeductionTypeNotFoundException(id));
    }

    /**
     * Creates a new deduction type. Prevents duplicates by name.
     *
     * @param deductionType The deduction type to create
     * @return Created DeductionType
     * @throws DuplicateDeductionTypeNameException if a type with the same name
     *                                             exists
     */
    public DeductionType createDeductionType(DeductionType deductionType) {
        deductionTypeRepository.findByNameIgnoreCase(deductionType.getName())
                .ifPresent(existing -> {
                    throw new DuplicateDeductionTypeNameException(deductionType.getName());
                });

        return deductionTypeRepository.save(deductionType);
    }

    /**
     * Updates an existing deduction type. Prevents name collision with another
     * type.
     *
     * @param id      ID of the deduction type to update
     * @param updated Updated deduction type data
     * @return Updated DeductionType
     * @throws DeductionTypeNotFoundException      if not found
     * @throws DuplicateDeductionTypeNameException if another type has the same name
     */
    public DeductionType updateDeductionType(Long id, DeductionType updated) {
        DeductionType existing = deductionTypeRepository.findById(id)
                .orElseThrow(() -> new DeductionTypeNotFoundException(id));

        deductionTypeRepository.findByNameIgnoreCase(updated.getName()).ifPresent(other -> {
            if (!other.getDeductionTypeId().equals(id)) {
                throw new DuplicateDeductionTypeNameException(updated.getName());
            }
        });

        existing.setName(updated.getName());
        existing.setPercentage(updated.getPercentage());

        return deductionTypeRepository.save(existing);
    }

    /**
     * Deletes a deduction type by its ID.
     *
     * @param id Deduction type ID
     * @throws DeductionTypeNotFoundException if not found
     */
    public void deleteDeductionType(Long id) {
        if (!deductionTypeRepository.existsById(id)) {
            throw new DeductionTypeNotFoundException(id);
        }
        deductionTypeRepository.deleteById(id);
    }
    
}
