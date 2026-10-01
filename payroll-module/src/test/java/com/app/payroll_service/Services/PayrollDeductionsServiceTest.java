package com.app.payroll_service.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.app.payroll_service.models.PayrollDeductions;
import com.app.payroll_service.repository.PayrollDeductionsRepository;

@ExtendWith(MockitoExtension.class)
public class PayrollDeductionsServiceTest {

    @Mock
    private PayrollDeductionsRepository payrollDeductionsRepository;

    @InjectMocks
    private PayrollDeductionsService payrollDeductionsService;

    @Test
    public void testGetAllPayrollDeductions() {
        // Arrange
        PayrollDeductions pd1 = new PayrollDeductions();
        pd1.setPayrollDeductionId(1L);
        PayrollDeductions pd2 = new PayrollDeductions();
        pd2.setPayrollDeductionId(2L);

        when(payrollDeductionsRepository.findAll()).thenReturn(Arrays.asList(pd1, pd2));

        // Act
        List<PayrollDeductions> result = payrollDeductionsService.getAllPayrollDeductions();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(payrollDeductionsRepository, times(1)).findAll();
    }
}
