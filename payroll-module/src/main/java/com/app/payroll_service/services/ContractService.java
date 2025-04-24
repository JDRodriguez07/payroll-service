package com.app.payroll_service.services;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.app.payroll_service.dto.CreateContractDTO;
import com.app.payroll_service.dto.ContractResponseDTO;
import com.app.payroll_service.dto.UpdateContractDTO;
import com.app.payroll_service.enums.ContractStatusEnum;
import com.app.payroll_service.exceptions.ContractNotFoundException;
import com.app.payroll_service.exceptions.ContractTypeNotFoundException;
import com.app.payroll_service.exceptions.ScheduleNotFoundException;
import com.app.payroll_service.mapper.ContractMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.payroll_service.models.Contract;
import com.app.payroll_service.models.ContractType;
import com.app.payroll_service.models.Schedule;
import com.app.payroll_service.repository.ContractRepository;
import com.app.payroll_service.repository.ContractTypeRepository;
import com.app.payroll_service.repository.ScheduleRepository;

@Service
public class ContractService {

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ContractTypeRepository contractTypeRepository;

    @Autowired
    private ContractMapper contractMapper;

    public List<ContractResponseDTO> getAllContracts() {
        List<Contract> contracts = contractRepository.findAll();
        return contracts.stream()
                .map(contractMapper::toResponseDTO)
                .toList();
    }

    public ContractResponseDTO getContractById(Long contractId) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new ContractNotFoundException(contractId));
        return contractMapper.toResponseDTO(contract);
    }

    public ContractResponseDTO createContract(CreateContractDTO dto) {

        Schedule schedule = scheduleRepository.findById(dto.getScheduleId())
                .orElseThrow(() -> new ScheduleNotFoundException(dto.getScheduleId()));

        ContractType contractType = contractTypeRepository.findById(dto.getContractTypeId())
                .orElseThrow(() -> new ContractTypeNotFoundException(dto.getContractTypeId()));

        if (dto.getTerminationDate() != null && dto.getTerminationDate().isBefore(dto.getHireDate())) {
            throw new IllegalArgumentException("Termination date cannot be before hire date.");
        }

        Contract contract = contractMapper.toEntityContract(dto);

        contract.setSchedule(schedule);
        contract.setContractType(contractType);
        contract.setStatus(ContractStatusEnum.ACTIVE.getValue());

        int dailyHours = calculateDailyHours(schedule);
        contract.setDailyHours(dailyHours);

        Contract saved = contractRepository.save(contract);
        return contractMapper.toResponseDTO(saved);
    }

    public ContractResponseDTO updateContract(Long contractId, UpdateContractDTO dto) {
        Contract existingContract = contractRepository.findById(contractId)
                .orElseThrow(() -> new ContractNotFoundException(contractId));

        if (dto.getScheduleId() != null) {
            Schedule schedule = scheduleRepository.findById(dto.getScheduleId())
                    .orElseThrow(() -> new ScheduleNotFoundException(dto.getScheduleId()));
            existingContract.setSchedule(schedule);

            int dailyHours = calculateDailyHours(schedule);
            existingContract.setDailyHours(dailyHours);

        }

        if (dto.getHireDate() != null) {
            existingContract.setHireDate(dto.getHireDate());
        }

        if (dto.getTerminationDate() != null) {
            if (existingContract.getHireDate() != null
                    && dto.getTerminationDate().isBefore(existingContract.getHireDate())) {
                throw new IllegalArgumentException("Termination date cannot be before hire date.");
            }
            existingContract.setTerminationDate(dto.getTerminationDate());
        }

        if (dto.getSalary() != null) {
            existingContract.setSalary(dto.getSalary());
        }

        Contract updated = contractRepository.save(existingContract);
        return contractMapper.toResponseDTO(updated);

    }

    public ContractResponseDTO terminateContract(Long contractId) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new ContractNotFoundException(contractId));

        contract.setStatus(ContractStatusEnum.TERMINATED.getValue());
        contract.setTerminationDate(LocalDate.now());

        Contract updated = contractRepository.save(contract);
        return contractMapper.toResponseDTO(updated);
    }

    private int calculateDailyHours(Schedule schedule) {
        return (int) ChronoUnit.HOURS.between(schedule.getStartTime(), schedule.getEndTime());
    }

}
