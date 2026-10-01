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
import com.app.payroll_service.exceptions.InvalidTerminationDateException;
import com.app.payroll_service.exceptions.MissingTerminationDateException;
import com.app.payroll_service.exceptions.ScheduleNotFoundException;
import com.app.payroll_service.exceptions.TerminationDateNotAllowedException;
import com.app.payroll_service.mapper.ContractMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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

        // Si el tipo de contrato es indefinido (ID = 1), no se permite terminationDate
        if (contractType.getContractTypeId() == 1L && dto.getTerminationDate() != null) {
            throw new TerminationDateNotAllowedException(contractType.getContractTypeId());
        }

        // Validar si se requiere terminationDate
        if (contractType.isRequiresEndDate() && dto.getTerminationDate() == null) {
            throw new MissingTerminationDateException(dto.getContractTypeId());
        }

        if (dto.getTerminationDate() != null && dto.getTerminationDate().isBefore(dto.getHireDate())) {
            throw new InvalidTerminationDateException();
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

        // Validar y actualizar el horario
        if (dto.getScheduleId() != null) {
            Schedule schedule = scheduleRepository.findById(dto.getScheduleId())
                    .orElseThrow(() -> new ScheduleNotFoundException(dto.getScheduleId()));
            existingContract.setSchedule(schedule);
            int dailyHours = calculateDailyHours(schedule);
            existingContract.setDailyHours(dailyHours);
        }

        // Validar y actualizar la fecha de contratación
        if (dto.getHireDate() != null) {
            existingContract.setHireDate(dto.getHireDate());
        }

        boolean requiresEndDate = existingContract.getContractType().isRequiresEndDate();
        Long contractTypeId = existingContract.getContractType().getContractTypeId();

        // Validación: se requiere terminationDate y no se pasó
        if (requiresEndDate && dto.getTerminationDate() == null) {
            throw new MissingTerminationDateException(contractTypeId);
        }

        // Validación: no se permite terminationDate para ciertos tipos
        if (!requiresEndDate && dto.getTerminationDate() != null && contractTypeId == 1L) {
            throw new TerminationDateNotAllowedException(contractTypeId);
        }

        // Validación: coherencia de fechas
        if (dto.getTerminationDate() != null && existingContract.getHireDate() != null &&
                dto.getTerminationDate().isBefore(existingContract.getHireDate())) {
            throw new InvalidTerminationDateException();
        }

        // Actualizar terminationDate
        if (dto.getTerminationDate() != null) {
            existingContract.setTerminationDate(dto.getTerminationDate());
        }

        // Actualizar salario
        if (dto.getSalary() != null) {
            existingContract.setSalary(dto.getSalary());
        }

        Contract updated = contractRepository.save(existingContract);
        return contractMapper.toResponseDTO(updated);
    }

    //Pending
    public ContractResponseDTO terminateContract(Long contractId) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new ContractNotFoundException(contractId));

        contract.setStatus(ContractStatusEnum.TERMINATED.getValue());
        contract.setTerminationDate(LocalDate.now());

        Contract updated = contractRepository.save(contract);
        return contractMapper.toResponseDTO(updated);
    }

    /**
     * This scheduled method automatically terminates contracts
     * that have reached or passed their termination date.
     * It runs every day at 11:59 PM.
     */
    @Scheduled(cron = "59 23 * * *")
    public void autoTerminateDueContracts() {
        LocalDate today = LocalDate.now();

        List<Contract> contractsToTerminate = contractRepository
                .findByStatusAndTerminationDateBeforeOrTerminationDateEquals(
                        ContractStatusEnum.ACTIVE.getValue(), today, today);

        for (Contract contract : contractsToTerminate) {
            contract.setStatus(ContractStatusEnum.TERMINATED.getValue());
        }

        contractRepository.saveAll(contractsToTerminate);
    }

    private int calculateDailyHours(Schedule schedule) {
        return (int) ChronoUnit.HOURS.between(schedule.getStartTime(), schedule.getEndTime());
    }

}
