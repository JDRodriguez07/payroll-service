package com.app.payroll_service.services;

import com.app.payroll_service.client.ApiClient;
import com.app.payroll_service.dto.EmployeeAndContract;
import com.app.payroll_service.enums.ContractStatusEnum;
import com.app.payroll_service.exceptions.IsNotLastWorkDayOfMonthException;
import com.app.payroll_service.exceptions.PayrollNotFoundException;
import com.app.payroll_service.models.Contract;
import com.app.payroll_service.models.DeductionType;
import com.app.payroll_service.models.Payroll;
import com.app.payroll_service.repository.ContractRepository;
import com.app.payroll_service.repository.DeductionRepository;
import com.app.payroll_service.repository.DeductionTypeRepository;
import com.app.payroll_service.repository.PayrollDeductionsRepository;
import com.app.payroll_service.repository.PayrollRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PayrollServiceTest {

    @InjectMocks
    private PayrollService payrollService;

    @Mock
    private PayrollRepository payrollRepository;

    @Mock
    private ContractRepository contractRepository;

    @Mock
    private DeductionTypeRepository deductionTypeRepository;

    @Mock
    private DeductionRepository deductionRepository;

    @Mock
    private PayrollDeductionsRepository payrollDeductionsRepository;

    @Mock
    private ApiClient apiClient;

    private final String token = "Bearer fake-token";

    @Test
    void getAllPayrolls_ReturnsPayrollList() {
        when(payrollRepository.findAll()).thenReturn(List.of(new Payroll()));

        List<Payroll> result = payrollService.getAllPayrolls();

        assertEquals(1, result.size());
    }

    @Test
    void getPayrollById_ReturnsPayroll_WhenExists() {
        Payroll payroll = new Payroll();
        payroll.setPayrollId(1L);
        when(payrollRepository.findById(1L)).thenReturn(Optional.of(payroll));

        Payroll result = payrollService.getPayrollById(1L);

        assertEquals(1L, result.getPayrollId());
    }

    @Test
    void getPayrollById_ThrowsException_WhenNotFound() {
        when(payrollRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(PayrollNotFoundException.class, () -> payrollService.getPayrollById(99L));
    }

    @Test
    void generateMonthlyPayrolls_ThrowsException_IfNotLastBusinessDay() {
        LocalDate mockedToday = LocalDate.of(2024, 5, 30); // No es el último día hábil

        when(apiClient.allEmployeesAndContracts(token)).thenReturn(List.of()); // Evita NPE

        try (MockedStatic<LocalDate> mockDate = mockStatic(LocalDate.class)) {
            mockDate.when(LocalDate::now).thenReturn(mockedToday);

            assertThrows(IsNotLastWorkDayOfMonthException.class, () -> payrollService.generateMonthlyPayrolls(token));
        }
    }

    @Test
    void generateMonthlyPayrolls_CreatesPayrollsAndDeductions() {
        LocalDate mockedToday = LocalDate.of(2024, 5, 31);

        try (MockedStatic<LocalDate> mockDate = mockStatic(LocalDate.class)) {
            mockDate.when(LocalDate::now).thenReturn(mockedToday);

            // Mock Contract
            Contract contract = new Contract();
            contract.setContractId(1L);
            contract.setStatus(ContractStatusEnum.ACTIVE.getValue());
            contract.setSalary(new BigDecimal("1000.00"));

            when(contractRepository.findByStatus(ContractStatusEnum.ACTIVE.getValue()))
                    .thenReturn(List.of(contract));

            // Mock Deduction Types
            DeductionType pensionType = new DeductionType();
            pensionType.setDeductionTypeId(1L);
            pensionType.setPercentage(new BigDecimal("0.04"));
            pensionType.setName("Pension");

            DeductionType healthType = new DeductionType();
            healthType.setDeductionTypeId(2L);
            healthType.setPercentage(new BigDecimal("0.04"));
            healthType.setName("Salud");

            when(deductionTypeRepository.findByNameIgnoreCase("Pension")).thenReturn(Optional.of(pensionType));
            when(deductionTypeRepository.findByNameIgnoreCase("Salud")).thenReturn(Optional.of(healthType));

            // Mock API response
            EmployeeAndContract dto = new EmployeeAndContract();
            dto.setContractId(1L);
            dto.setEmployeeId(10L);

            when(apiClient.allEmployeesAndContracts(token)).thenReturn(List.of(dto));

            assertDoesNotThrow(() -> payrollService.generateMonthlyPayrolls(token));
        }
    }
    
}
