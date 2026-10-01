package com.app.payroll_service.services;

import com.app.payroll_service.dto.ContractTypeResponseDTO;
import com.app.payroll_service.exceptions.ContractTypeNotFoundException;
import com.app.payroll_service.mapper.ContractTypeMapper;
import com.app.payroll_service.models.ContractType;
import com.app.payroll_service.repository.ContractTypeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractTypeService {

    @Autowired
    private ContractTypeRepository contractTypeRepository;

    @Autowired
    private ContractTypeMapper contractTypeMapper;

    public List<ContractTypeResponseDTO> getAllContractTypes() {
        List<ContractType> types = contractTypeRepository.findAll();
        return contractTypeMapper.toResponseDTOList(types);
    }

    public ContractTypeResponseDTO getContractTypeById(Long id) {
        ContractType type = contractTypeRepository.findById(id)
                .orElseThrow(() -> new ContractTypeNotFoundException(id));
        return contractTypeMapper.toResponseDTO(type);
    }

}
