package com.app.payroll_service.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.app.payroll_service.dto.ContractTypeResponseDTO;
import com.app.payroll_service.exceptions.ContractTypeNotFoundException;
import com.app.payroll_service.mapper.ContractTypeMapper;
import com.app.payroll_service.models.ContractType;
import com.app.payroll_service.repository.ContractTypeRepository;

class ContractTypeServiceTest {

    @Mock
    private ContractTypeRepository contractTypeRepository;

    @Mock
    private ContractTypeMapper contractTypeMapper;

    @InjectMocks
    private ContractTypeService contractTypeService;

    private ContractType contractType;
    private ContractTypeResponseDTO contractTypeResponseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        contractType = new ContractType();
        contractType.setContractTypeId(1L);
        contractType.setName("Full-time");

        contractTypeResponseDTO = new ContractTypeResponseDTO();
        contractTypeResponseDTO.setContractTypeId(1L);
        contractTypeResponseDTO.setContractTypeName("Full-time");
    }

    @Test
    void testGetAllContractTypes() {
        when(contractTypeRepository.findAll()).thenReturn(List.of(contractType));
        when(contractTypeMapper.toResponseDTOList(List.of(contractType))).thenReturn(List.of(contractTypeResponseDTO));

        List<ContractTypeResponseDTO> result = contractTypeService.getAllContractTypes();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Full-time", result.get(0).getContractTypeName());
        verify(contractTypeRepository, times(1)).findAll();
        verify(contractTypeMapper, times(1)).toResponseDTOList(List.of(contractType));
    }

    @Test
    void testGetContractTypeById_Found() {
        when(contractTypeRepository.findById(1L)).thenReturn(Optional.of(contractType));
        when(contractTypeMapper.toResponseDTO(contractType)).thenReturn(contractTypeResponseDTO);

        ContractTypeResponseDTO result = contractTypeService.getContractTypeById(1L);

        assertNotNull(result);
        assertEquals("Full-time", result.getContractTypeName());
        verify(contractTypeRepository).findById(1L);
        verify(contractTypeMapper).toResponseDTO(contractType);
    }

    @Test
    void testGetContractTypeById_NotFound() {
        when(contractTypeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ContractTypeNotFoundException.class, () -> {
            contractTypeService.getContractTypeById(99L);
        });

        verify(contractTypeRepository).findById(99L);
        verifyNoMoreInteractions(contractTypeMapper);
    }
}
