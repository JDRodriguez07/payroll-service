package com.app.payroll_service.services;

import java.util.Date;
import java.util.List;

import com.app.payroll_service.exceptions.ContractNotFoundException;
import com.app.payroll_service.exceptions.ScheduleNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.payroll_service.models.Contract;
import com.app.payroll_service.models.Schedule;
import com.app.payroll_service.repository.ContractRepository;
import com.app.payroll_service.repository.ScheduleRepository;

@Service
public class ContractService {

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

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
        if (!contractRepository.existsById(contractId)) {
            throw new ContractNotFoundException(contractId);
        }
        contractRepository.deleteById(contractId);
    }

    public Contract updateContract(Long contractId, Contract contract) {
        Contract existingContract = contractRepository.findById(contractId)
                .orElseThrow(() -> new ContractNotFoundException(contractId));

        existingContract.setSchedule(contract.getSchedule());
        existingContract.setContractType(contract.getContractType());
        existingContract.setHireDate(contract.getHireDate());
        existingContract.setTerminationDate(contract.getTerminationDate());
        // existingContract.setDailyHours(contract.getDailyHours());
        existingContract.setSalary(contract.getSalary());

        return contractRepository.save(existingContract);
    }

    public Contract terminateContract(Long contractId) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new ContractNotFoundException(contractId));

        // Marcar como terminado
        contract.setStatus("Terminado");
        contract.setTerminationDate(new Date());

        Contract updated = contractRepository.save(contract);

        // 🔔 Punto de integración (comunicación futura)
        // logContractTerminationEvent(updated);

        return updated;
    }

    public Contract assignScheduleToContract(Long contractId, Long scheduleId) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new ContractNotFoundException(contractId));

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleNotFoundException(scheduleId));

        contract.setSchedule(schedule);

        return contractRepository.save(contract);
    }

}
