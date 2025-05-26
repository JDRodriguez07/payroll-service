package com.app.payroll_service.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.app.payroll_service.dto.DeductionTypeResponseDTO;
import com.app.payroll_service.exceptions.DeductionTypeNotFoundException;
import com.app.payroll_service.mapper.DeductionTypeMapper;
import com.app.payroll_service.models.DeductionType;
import com.app.payroll_service.repository.DeductionTypeRepository;
import com.app.payroll_service.services.DeductionTypeService;

@ExtendWith(MockitoExtension.class)
class DeductionTypeServiceTest {

    @Mock
    private DeductionTypeRepository deductionTypeRepository;

    @Mock
    private DeductionTypeMapper deductionTypeMapper;

    @InjectMocks
    private DeductionTypeService deductionTypeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllDeductionTypes() {
        DeductionType type1 = new DeductionType();
        DeductionType type2 = new DeductionType();
        List<DeductionType> types = Arrays.asList(type1, type2);

        DeductionTypeResponseDTO dto1 = new DeductionTypeResponseDTO();
        DeductionTypeResponseDTO dto2 = new DeductionTypeResponseDTO();
        List<DeductionTypeResponseDTO> dtos = Arrays.asList(dto1, dto2);

        when(deductionTypeRepository.findAll()).thenReturn(types);
        when(deductionTypeMapper.toResponseDTOList(types)).thenReturn(dtos);

        List<DeductionTypeResponseDTO> result = deductionTypeService.getAllDeductionTypes();

        assertEquals(dtos, result);
        verify(deductionTypeRepository).findAll();
        verify(deductionTypeMapper).toResponseDTOList(types);
    }

    @Test
    void testGetDeductionTypeById_Found() {
        
        Long id = 1L;
        DeductionType type = new DeductionType();
        DeductionTypeResponseDTO dto = new DeductionTypeResponseDTO();

        when(deductionTypeRepository.findById(id)).thenReturn(Optional.of(type));
        when(deductionTypeMapper.toResponseDTO(type)).thenReturn(dto);

        DeductionTypeResponseDTO result = deductionTypeService.getDeductionTypeById(id);

        assertEquals(dto, result);
        verify(deductionTypeRepository).findById(id);
        verify(deductionTypeMapper).toResponseDTO(type);
    }

    @Test
    void testGetDeductionTypeById_NotFound() {
      
        Long id = 100L;
        when(deductionTypeRepository.findById(id)).thenReturn(Optional.empty());

  
        DeductionTypeNotFoundException ex = assertThrows(DeductionTypeNotFoundException.class, () -> {
            deductionTypeService.getDeductionTypeById(id);
        });

        assertEquals("DeductionType not found with id: 100", ex.getMessage());
        verify(deductionTypeRepository).findById(id);
    }
}