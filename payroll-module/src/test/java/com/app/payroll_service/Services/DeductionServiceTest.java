package com.app.payroll_service.services;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.app.payroll_service.dto.DeductionResponseDTO;
import com.app.payroll_service.exceptions.DeductionNotFoundException;
import com.app.payroll_service.mapper.DeductionMapper;
import com.app.payroll_service.models.PayrollDeductions;
import com.app.payroll_service.repository.PayrollDeductionsRepository;

class DeductionServiceTest {

    @Mock
    private PayrollDeductionsRepository repository;

    @Mock
    private DeductionMapper mapper;

    @InjectMocks
    private DeductionService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testGetDeductionById() {
        // Arrange
        Long id = 1L;
        PayrollDeductions deduction = new PayrollDeductions();
        DeductionResponseDTO dto = new DeductionResponseDTO();
    
        when(repository.findById(id)).thenReturn(Optional.of(deduction));
        when(mapper.toResponseDTO(deduction)).thenReturn(dto);
    
        // Act
        DeductionResponseDTO result = service.getDeductionById(id);
    
        // Assert
        assertEquals(dto, result);
    }
    @Test
void testGetDeductionById_NotFound() {
    Long id = 100L;

    when(repository.findById(id)).thenReturn(Optional.empty());

    DeductionNotFoundException exception = assertThrows(DeductionNotFoundException.class, () -> {
        service.getDeductionById(id);
    });

    assertEquals("Deduction not found with id " + id, exception.getMessage());
}

@Test
void testGetAllDeductions() {
    PayrollDeductions d1 = new PayrollDeductions(); 
    PayrollDeductions d2 = new PayrollDeductions();
    List<PayrollDeductions> deductions = Arrays.asList(d1, d2);

    DeductionResponseDTO dto1 = new DeductionResponseDTO();
    DeductionResponseDTO dto2 = new DeductionResponseDTO();
    List<DeductionResponseDTO> responseDTOs = Arrays.asList(dto1, dto2);

    when(repository.findAll()).thenReturn(deductions);
    when(mapper.toResponseDTOListFromPayroll(deductions)).thenReturn(responseDTOs);

    List<DeductionResponseDTO> result = service.getAllDeductions();

    assertEquals(2, result.size());
    assertEquals(responseDTOs, result);
}
}
