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

    /*
     * 
    public ContractType createContractType(ContractType newType) {
        // Validación básica de campos obligatorios
        if (newType.getName() == null || newType.getName().isEmpty()) {
            throw new IllegalArgumentException("El nombre del tipo de contrato es obligatorio");
        }
        // (Opcional) Verificar duplicados por nombre
        if (contractTypeRepository.existsByName(newType.getName())) {
            throw new RuntimeException("Ya existe un tipo de contrato con ese nombre");
        }
        // Guardar la entidad
        return contractTypeRepository.save(newType);
    }
    */

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
