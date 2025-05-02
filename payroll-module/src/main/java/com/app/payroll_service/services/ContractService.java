package com.app.payroll_service.services;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.app.payroll_service.dto.CreateContractDTO;
import com.app.payroll_service.dto.ContractResponseDTO;
import com.app.payroll_service.dto.UpdateContractDTO;
import com.app.payroll_service.enums.ContractStatusEnum;
import com.app.payroll_service.exceptions.*;
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

/**
 * Service class responsible for managing contract operations such as creation,
 * updating, termination, and scheduled validations.
 */
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

    /**
     * Retrieves all contracts from the database.
     *
     * @return list of ContractResponseDTOs
     */
    public List<ContractResponseDTO> getAllContracts() {
        List<Contract> contracts = contractRepository.findAll();
        return contracts.stream()
                .map(contractMapper::toResponseDTO)
                .toList();
    }

    /**
     * Retrieves a specific contract by its ID.
     *
     * @param contractId the ID of the contract
     * @return ContractResponseDTO
     */
    public ContractResponseDTO getContractById(Long contractId) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new ContractNotFoundException(contractId));
        return contractMapper.toResponseDTO(contract);
    }

    /**
     * Creates a new contract based on the provided DTO.
     *
     * @param dto the contract creation data
     * @return the created contract as a DTO
     */
    public ContractResponseDTO createContract(CreateContractDTO dto) {

        Schedule schedule = scheduleRepository.findById(dto.getScheduleId())
                .orElseThrow(() -> new ScheduleNotFoundException(dto.getScheduleId()));

        ContractType contractType = contractTypeRepository.findById(dto.getContractTypeId())
                .orElseThrow(() -> new ContractTypeNotFoundException(dto.getContractTypeId()));

        // If contract type is indefinite (ID = 1), terminationDate must not be set
        if (contractType.getContractTypeId() == 1L && dto.getTerminationDate() != null) {
            throw new TerminationDateNotAllowedException(contractType.getContractTypeId());
        }

        // If the contract type requires a termination date, validate it exists
        if (contractType.isRequiresEndDate() && dto.getTerminationDate() == null) {
            throw new MissingTerminationDateException(dto.getContractTypeId());
        }

        // Termination date cannot be before hire date
        if (dto.getTerminationDate() != null && dto.getTerminationDate().isBefore(dto.getHireDate())) {
            throw new InvalidTerminationDateException();
        }

        Contract contract = contractMapper.toEntityContract(dto);
        contract.setSchedule(schedule);
        contract.setContractType(contractType);
        contract.setStatus(ContractStatusEnum.ACTIVE.getValue());
        contract.setDailyHours(calculateDailyHours(schedule));

        Contract saved = contractRepository.save(contract);
        return contractMapper.toResponseDTO(saved);
    }

    /**
     * Updates an existing contract based on the provided DTO.
     *
     * @param contractId the ID of the contract to update
     * @param dto        the update data
     * @return the updated contract as a DTO
     */
    public ContractResponseDTO updateContract(Long contractId, UpdateContractDTO dto) {
        Contract existingContract = contractRepository.findById(contractId)
                .orElseThrow(() -> new ContractNotFoundException(contractId));

        // Update schedule if provided
        if (dto.getScheduleId() != null) {
            Schedule schedule = scheduleRepository.findById(dto.getScheduleId())
                    .orElseThrow(() -> new ScheduleNotFoundException(dto.getScheduleId()));
            existingContract.setSchedule(schedule);
            existingContract.setDailyHours(calculateDailyHours(schedule));
        }

        // Update hire date if provided
        if (dto.getHireDate() != null) {
            existingContract.setHireDate(dto.getHireDate());
        }

        boolean requiresEndDate = existingContract.getContractType().isRequiresEndDate();
        Long contractTypeId = existingContract.getContractType().getContractTypeId();

        // If contract type requires termination date but it's not provided
        if (requiresEndDate && dto.getTerminationDate() == null) {
            throw new MissingTerminationDateException(contractTypeId);
        }

        // If contract type doesn't allow termination date but it is provided
        if (!requiresEndDate && dto.getTerminationDate() != null && contractTypeId == 1L) {
            throw new TerminationDateNotAllowedException(contractTypeId);
        }

        // Check that termination date is not before hire date
        if (dto.getTerminationDate() != null && existingContract.getHireDate() != null &&
                dto.getTerminationDate().isBefore(existingContract.getHireDate())) {
            throw new InvalidTerminationDateException();
        }

        // Update termination date
        if (dto.getTerminationDate() != null) {
            existingContract.setTerminationDate(dto.getTerminationDate());
        }

        // Update salary
        if (dto.getSalary() != null) {
            existingContract.setSalary(dto.getSalary());
        }

        Contract updated = contractRepository.save(existingContract);
        return contractMapper.toResponseDTO(updated);
    }

    /**
     * Manually terminates a contract if it is currently active.
     *
     * @param contractId the ID of the contract to terminate
     * @return the terminated contract as a DTO
     */
    public ContractResponseDTO terminateContractManually(Long contractId) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new ContractNotFoundException(contractId));

        if (!ContractStatusEnum.ACTIVE.getValue().equals(contract.getStatus())) {
            throw new ContractAlreadyTerminatedException(contractId);
        }

        if (contract.getTerminationDate() != null && contract.getTerminationDate().isBefore(LocalDate.now())) {
            throw new ContractAlreadyFinalizedException(contractId);
        }

        contract.setStatus(ContractStatusEnum.TERMINATED.getValue());
        contract.setTerminationDate(LocalDate.now());

        Contract terminated = contractRepository.save(contract);
        return contractMapper.toResponseDTO(terminated);
    }

    /**
     * Automatically terminates contracts that have reached or passed their
     * termination date.
     * Scheduled to run every day at 11:59 PM.
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

    /**
     * Calculates the number of working hours per day based on the given schedule.
     *
     * @param schedule the work schedule
     * @return number of daily working hours
     */
    private int calculateDailyHours(Schedule schedule) {
        return (int) ChronoUnit.HOURS.between(schedule.getStartTime(), schedule.getEndTime());
    }

}
