package com.app.payroll_service.services;

import java.util.List;

import com.app.payroll_service.exceptions.ContractNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.payroll_service.models.Contract;
import com.app.payroll_service.repository.ContractRepository;

@Service
public class ContractService {

    @Autowired
    private ContractRepository contractRepository;

    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }

    public Contract getContractById(Long contractId) {
        return contractRepository.findById(contractId)
                .orElseThrow(() -> new ContractNotFoundException(contractId));
    }

    public Contract createContract(Contract contract) {
        return contractRepository.save(contract);
    }

    public void deleteContract(Long contractId) {
        if (contractRepository.existsById(contractId)) {
            contractRepository.deleteById(contractId);
        } else {
            throw new ContractNotFoundException(contractId);
        }
    }

    public Contract updateContract(Long contractId, Contract contract) {
        Contract existingContract = contractRepository.findById(contractId)
                .orElseThrow(() -> new ContractNotFoundException(contractId));

        existingContract.setSchedule(contract.getSchedule());
        existingContract.setContractType(contract.getContractType());
        existingContract.setHireDate(contract.getHireDate());
        existingContract.setTerminationDate(contract.getTerminationDate());
        existingContract.setDailyHours(contract.getDailyHours());
        existingContract.setSalary(contract.getSalary());

        return contractRepository.save(existingContract);
    }
}
