package com.app.payroll_service.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.app.payroll_service.dto.ContractResponseDTO;
import com.app.payroll_service.dto.CreateContractDTO;
import com.app.payroll_service.dto.UpdateContractDTO;
import com.app.payroll_service.exceptions.ContractAlreadyTerminatedException;
import com.app.payroll_service.exceptions.ContractNotFoundException;
import com.app.payroll_service.exceptions.InvalidTerminationDateException;
import com.app.payroll_service.exceptions.MissingTerminationDateException;
import com.app.payroll_service.exceptions.ScheduleNotFoundException;
import com.app.payroll_service.exceptions.TerminationDateNotAllowedException;
import com.app.payroll_service.mapper.ContractMapper;
import com.app.payroll_service.models.Contract;
import com.app.payroll_service.models.ContractType;
import com.app.payroll_service.models.Schedule;
import com.app.payroll_service.repository.ContractRepository;
import com.app.payroll_service.repository.ContractTypeRepository;
import com.app.payroll_service.repository.ScheduleRepository;
import com.app.payroll_service.services.ContractService;


@ExtendWith(MockitoExtension.class)
class ContractServiceTest {
    @Mock
    private ContractMapper contractMapper;

    @Mock
    private ContractRepository contractRepository;

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private ContractTypeRepository contractTypeRepository;

    @InjectMocks
    private ContractService contractService;

    private Contract contract;
    private Schedule schedule;
    private ContractType contractType;

    @BeforeEach
    void setUp() {
        schedule = new Schedule();
        schedule.setScheduleId(1L);
        schedule.setStartTime(java.time.LocalTime.of(9, 0));
        schedule.setEndTime(java.time.LocalTime.of(17, 0));

        contractType = new ContractType();
        contractType.setContractTypeId(1L);
        contractType.setName("Full-Time");
        contractType.setRequiresEndDate(true);

        contract = new Contract();
        contract.setContractId(1L);
        contract.setSchedule(schedule);
        contract.setContractType(contractType);
        contract.setHireDate(LocalDate.now().minusDays(30));
        contract.setTerminationDate(null);
        contract.setSalary(BigDecimal.valueOf(3000));
        contract.setStatus("ACTIVO");
        contract.setDailyHours(8);
    }

    @Test
void testUpdateContract_valid() {
    UpdateContractDTO dto = new UpdateContractDTO();
    dto.setScheduleId(1L);
    dto.setHireDate(LocalDate.now().minusDays(60));
    dto.setSalary(BigDecimal.valueOf(3500));
    dto.setTerminationDate(LocalDate.now().plusDays(30));

    when(contractRepository.findById(1L)).thenReturn(Optional.of(contract));
    when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));
    when(contractRepository.save(any())).thenReturn(contract);
    when(contractMapper.toResponseDTO(any())).thenReturn(new ContractResponseDTO());

    contractService.updateContract(1L, dto);

    verify(contractRepository).save(any());
}
@Test
    void getAllContracts_ReturnsMappedList() {
        Contract contract = new Contract();
        ContractResponseDTO dto = new ContractResponseDTO();

        when(contractRepository.findAll()).thenReturn(List.of(contract));
        when(contractMapper.toResponseDTO(contract)).thenReturn(dto);

        List<ContractResponseDTO> result = contractService.getAllContracts();

        assertEquals(1, result.size());
        verify(contractRepository).findAll();
    }

    // Test getContractById() with valid ID
    @Test
    void getContractById_ReturnsContract_WhenExists() {
        Long contractId = 1L;
        Contract contract = new Contract();
        ContractResponseDTO dto = new ContractResponseDTO();

        when(contractRepository.findById(contractId)).thenReturn(Optional.of(contract));
        when(contractMapper.toResponseDTO(contract)).thenReturn(dto);

        ContractResponseDTO result = contractService.getContractById(contractId);

        assertNotNull(result);
        verify(contractRepository).findById(contractId);
    }

    // Test getContractById() with invalid ID
    @Test
    void getContractById_ThrowsException_WhenNotFound() {
        when(contractRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ContractNotFoundException.class, () -> contractService.getContractById(99L));
    }

   

    // Test createContract() - throws MissingTerminationDateException
    @Test
    void createContract_ThrowsMissingTerminationDateException() {
        CreateContractDTO dto = new CreateContractDTO();
        dto.setScheduleId(1L);
        dto.setContractTypeId(2L); // Requiere fecha de fin
        dto.setHireDate(LocalDate.of(2024, 1, 1));
        dto.setTerminationDate(null);

        ContractType contractType = new ContractType();
        contractType.setContractTypeId(2L);
        contractType.setRequiresEndDate(true);

        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(new Schedule()));
        when(contractTypeRepository.findById(2L)).thenReturn(Optional.of(contractType));

        assertThrows(MissingTerminationDateException.class, () -> contractService.createContract(dto));
    }

    // Test createContract() - throws TerminationDateNotAllowedException
    @Test
    void createContract_ThrowsTerminationDateNotAllowedException() {
        CreateContractDTO dto = new CreateContractDTO();
        dto.setScheduleId(1L);
        dto.setContractTypeId(1L); // Indefinido
        dto.setHireDate(LocalDate.of(2024, 1, 1));
        dto.setTerminationDate(LocalDate.of(2024, 12, 31)); // No permitida

        ContractType contractType = new ContractType();
        contractType.setContractTypeId(1L); // Indefinido
        contractType.setRequiresEndDate(false);

        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(new Schedule()));
        when(contractTypeRepository.findById(1L)).thenReturn(Optional.of(contractType));

        assertThrows(TerminationDateNotAllowedException.class, () -> contractService.createContract(dto));
    }

    // Test createContract() - throws InvalidTerminationDateException
    @Test
    void createContract_ThrowsInvalidTerminationDateException() {
        CreateContractDTO dto = new CreateContractDTO();
        dto.setScheduleId(1L);
        dto.setContractTypeId(2L);
        dto.setHireDate(LocalDate.of(2024, 5, 1));
        dto.setTerminationDate(LocalDate.of(2024, 1, 1)); // Antes de hireDate

        ContractType contractType = new ContractType();
        contractType.setContractTypeId(2L);
        contractType.setRequiresEndDate(true);

        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(new Schedule()));
        when(contractTypeRepository.findById(2L)).thenReturn(Optional.of(contractType));

        assertThrows(InvalidTerminationDateException.class, () -> contractService.createContract(dto));
    }

    @Test
    void createContract_ThrowsScheduleNotFoundException() {
        CreateContractDTO dto = new CreateContractDTO();
        dto.setScheduleId(99L);
        dto.setContractTypeId(1L);
        dto.setHireDate(LocalDate.now());
    
        when(scheduleRepository.findById(99L)).thenReturn(Optional.empty());
    
        assertThrows(ScheduleNotFoundException.class, () -> contractService.createContract(dto));
    }
    @Test
    void testUpdateContract_missingTerminationDate() {
        UpdateContractDTO dto = new UpdateContractDTO();
        dto.setScheduleId(1L);
        dto.setHireDate(LocalDate.now().minusDays(10));
        dto.setSalary(BigDecimal.valueOf(2500));
        dto.setTerminationDate(null);

        when(contractRepository.findById(1L)).thenReturn(Optional.of(contract));
        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));

        assertThrows(MissingTerminationDateException.class, () ->
                contractService.updateContract(1L, dto));
    }

    @Test
    void testTerminateContractManually_valid() {
        when(contractRepository.findById(1L)).thenReturn(Optional.of(contract));
        when(contractRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
    
        ContractResponseDTO response = contractService.terminateContractManually(1L);
    
        assertEquals("TERMINADO", contract.getStatus());
        assertEquals(LocalDate.now(), contract.getTerminationDate());
        verify(contractRepository).save(contract);
    }

    @Test
    void testTerminateContractManually_contractAlreadyFinalized() {
        contract.setTerminationDate(LocalDate.now().minusDays(1));
        contract.setStatus("TERMINADO");
        when(contractRepository.findById(1L)).thenReturn(Optional.of(contract));

        assertThrows(ContractAlreadyTerminatedException.class, () ->
                contractService.terminateContractManually(1L));
    }
    @Test
    void createContract_SuccessfullyCreatesContract() {
        CreateContractDTO dto = new CreateContractDTO();
        dto.setScheduleId(1L);
        dto.setContractTypeId(2L);
        dto.setHireDate(LocalDate.of(2024, 1, 1));
        dto.setTerminationDate(LocalDate.of(2024, 12, 31));
    
        Schedule schedule = new Schedule();
        schedule.setStartTime(LocalTime.of(8, 0));  // <--- Asignar tiempo inicio
        schedule.setEndTime(LocalTime.of(17, 0));   // <--- Asignar tiempo fin
    
        ContractType contractType = new ContractType();
        contractType.setContractTypeId(2L);
        contractType.setRequiresEndDate(true);
    
        Contract contract = new Contract();
        contract.setHireDate(dto.getHireDate());
        contract.setTerminationDate(dto.getTerminationDate());
        contract.setSchedule(schedule);
        contract.setContractType(contractType);
    
        Contract saved = new Contract();
        saved.setHireDate(dto.getHireDate());
        saved.setTerminationDate(dto.getTerminationDate());
        saved.setSchedule(schedule);
        saved.setContractType(contractType);
    
        ContractResponseDTO response = new ContractResponseDTO();
    
        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));
        when(contractTypeRepository.findById(2L)).thenReturn(Optional.of(contractType));
        when(contractMapper.toEntityContract(dto)).thenReturn(contract);
        when(contractRepository.save(any(Contract.class))).thenReturn(saved);
        when(contractMapper.toResponseDTO(saved)).thenReturn(response);
    
        ContractResponseDTO result = contractService.createContract(dto);
    
        assertNotNull(result);
        verify(contractRepository).save(any(Contract.class));
    }
    @Test
    void testAutoTerminateDueContracts() {
        Contract contractToTerminate = new Contract();
        contractToTerminate.setContractId(2L);
        contractToTerminate.setTerminationDate(LocalDate.now());
        contractToTerminate.setStatus("ACTIVO");

        when(contractRepository.findByStatusAndTerminationDateLessThanEqual(eq("ACTIVO"), any(LocalDate.class)))
                .thenReturn(List.of(contractToTerminate));

        contractService.autoTerminateDueContracts();

        verify(contractRepository).saveAll(any());
    }

    

    
}
