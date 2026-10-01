package com.app.payroll_service.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.payroll_service.exceptions.ContractTypeNotFoundException;
import com.app.payroll_service.models.ContractType;
import com.app.payroll_service.repository.ContractTypeRepository;

@Service
public class ContractTypeService {

    @Autowired
    private ContractTypeRepository contractTypeRepository;

    public List<ContractType> getAllContractsType() {
        return contractTypeRepository.findAll();
    }

    public ContractType getContractTypeById(Long id) {
        return contractTypeRepository.findById(id)
                .orElseThrow(() -> new ContractTypeNotFoundException(id));
    }

    public ContractType createContractType(ContractType contractType) {
        return contractTypeRepository.save(contractType);
    }

    public void deleteContractType(Long id) {
        if (!contractTypeRepository.existsById(id)) {
            throw new ContractTypeNotFoundException(id);
        }
        contractTypeRepository.deleteById(id);
    }

    public ContractType updateContractType(Long id, ContractType contractType) {
        ContractType existingContractType = contractTypeRepository.findById(id)
                .orElseThrow(() -> new ContractTypeNotFoundException(id));

        existingContractType.setName(contractType.getName());
        existingContractType.setRequiresEndDate(contractType.isRequiresEndDate());

        return contractTypeRepository.save(existingContractType);
    }

}
