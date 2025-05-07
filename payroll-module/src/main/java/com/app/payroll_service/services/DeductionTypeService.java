package com.app.payroll_service.services;

import com.app.payroll_service.dto.DeductionTypeResponseDTO;
import com.app.payroll_service.exceptions.DeductionTypeNotFoundException;
import com.app.payroll_service.mapper.DeductionTypeMapper;
import com.app.payroll_service.models.DeductionType;
import com.app.payroll_service.repository.DeductionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeductionTypeService {

    @Autowired
    private DeductionTypeRepository deductionTypeRepository;

    @Autowired
    private DeductionTypeMapper deductionTypeMapper;

    /**
     * Retrieves all deduction types and maps them to response DTOs.
     *
     * @return a list of DeductionTypeResponseDTO
     */
    public List<DeductionTypeResponseDTO> getAllDeductionTypes() {
        List<DeductionType> types = deductionTypeRepository.findAll();
        return deductionTypeMapper.toResponseDTOList(types);
    }

    /**
     * Retrieves a deduction type by its ID and maps it to a response DTO.
     *
     * @param id the ID of the deduction type to retrieve
     * @return the DeductionTypeResponseDTO
     * @throws DeductionTypeNotFoundException if the deduction type does not exist
     */
    public DeductionTypeResponseDTO getDeductionTypeById(Long id) {
        DeductionType type = deductionTypeRepository.findById(id)
                .orElseThrow(() -> new DeductionTypeNotFoundException(id));
        return deductionTypeMapper.toResponseDTO(type);
    }
    
}
