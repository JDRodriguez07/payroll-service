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

    private ContractMapper contractMapper;

    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }

    public Contract getContractById(Long contractId) {
        return contractRepository.findById(contractId)
                .orElseThrow(() -> new ContractNotFoundException(contractId));
    }

    public ContractResponseDTO createContract(CreateContractDTO dto) {
        // Validar relaciones
        Schedule schedule = scheduleRepository.findById(dto.getScheduleId())
                .orElseThrow(() -> new ScheduleNotFoundException(dto.getScheduleId()));

        ContractType contractType = contractTypeRepository.findById(dto.getContractTypeId())
                .orElseThrow(() -> new ContractTypeNotFoundException(dto.getContractTypeId()));

        // Validar fechas
        if (dto.getTerminationDate() != null && dto.getTerminationDate().isBefore(dto.getHireDate())) {
            throw new IllegalArgumentException("Termination date cannot be before hire date.");
        }

        // MapStruct mapea los campos simples
        Contract contract = contractMapper.toEntityContract(dto);

        // Asignar relaciones y campos adicionales
        contract.setSchedule(schedule);
        contract.setContractType(contractType);
        contract.setStatus(ContractStatusEnum.ACTIVE.getValue());

        // Calcular horas diarias a partir del horario
        int dailyHours = (int) ChronoUnit.HOURS.between(schedule.getStartTime(), schedule.getEndTime());
        contract.setDailyHours(dailyHours);

        // Guardar y retornar
        Contract saved = contractRepository.save(contract);
        return mapToResponseDTO(saved);
    }

    public ContractResponseDTO updateContract(Long contractId, UpdateContractDTO dto) {
        Contract existingContract = contractRepository.findById(contractId)
                .orElseThrow(() -> new ContractNotFoundException(contractId));

        // Solo actualizar si se incluye un nuevo horario
        if (dto.getScheduleId() != null) {
            Schedule schedule = scheduleRepository.findById(dto.getScheduleId())
                    .orElseThrow(() -> new ScheduleNotFoundException(dto.getScheduleId()));
            existingContract.setSchedule(schedule);

            // Recalcular horas diarias si cambia el horario
            int dailyHours = (int) ChronoUnit.HOURS.between(
                    schedule.getStartTime(), schedule.getEndTime());
            existingContract.setDailyHours(dailyHours);
        }

        // Solo actualizar si se incluye una nueva fecha de contratación
        if (dto.getHireDate() != null) {
            existingContract.setHireDate(dto.getHireDate());
        }

        // Validar y asignar terminationDate
        if (dto.getTerminationDate() != null) {
            if (existingContract.getHireDate() != null
                    && dto.getTerminationDate().isBefore(existingContract.getHireDate())) {
                throw new IllegalArgumentException("Termination date cannot be before hire date.");
            }
            existingContract.setTerminationDate(dto.getTerminationDate());
        }

        // Solo actualizar si se incluye un nuevo salario
        if (dto.getSalary() != null) {
            existingContract.setSalary(dto.getSalary());
        }

        Contract updated = contractRepository.save(existingContract);
        return mapToResponseDTO(updated);
    }

    public Contract terminateContract(Long contractId) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new ContractNotFoundException(contractId));

        contract.setStatus(ContractStatusEnum.TERMINATED.getValue());
        contract.setTerminationDate(LocalDate.now());

        Contract updated = contractRepository.save(contract);

        // Punto de integración (comunicación futura)
        // logContractTerminationEvent(updated);

        return updated;
    }

    private ContractResponseDTO mapToResponseDTO(Contract contract) {
        ContractResponseDTO dto = new ContractResponseDTO();

        dto.setContractId(contract.getContractId());
        dto.setContractTypeName(contract.getContractType().getName());
        dto.setStartTime(contract.getSchedule().getStartTime());
        dto.setEndTime(contract.getSchedule().getEndTime());
        dto.setStatus(contract.getStatus());
        dto.setHireDate(contract.getHireDate());
        dto.setTerminationDate(contract.getTerminationDate());
        dto.setSalary(contract.getSalary());

        return dto;
    }

}
