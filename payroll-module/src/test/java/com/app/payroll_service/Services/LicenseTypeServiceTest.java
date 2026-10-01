package com.app.payroll_service.Services;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import com.app.payroll_service.dto.LicenseTypeResponseDTO;
import com.app.payroll_service.exceptions.LicenseTypeNotFoundException;
import com.app.payroll_service.mapper.LicenseTypeMapper;
import com.app.payroll_service.models.LicenseType;
import com.app.payroll_service.repository.LicenseTypeRepository;
import com.app.payroll_service.services.LicenseTypeService;

public class LicenseTypeServiceTest {

    @InjectMocks
    private LicenseTypeService licenseTypeService;

    @Mock
    private LicenseTypeRepository licenseTypeRepository;

    @Mock
    private LicenseTypeMapper licenseTypeMapper;

    private LicenseType licenseType;
    private LicenseTypeResponseDTO licenseTypeResponseDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        licenseType = new LicenseType();
        licenseType.setLicenseTypeId(1L);
        licenseType.setDescription("Vacation");

        licenseTypeResponseDTO = new LicenseTypeResponseDTO();
        licenseTypeResponseDTO.setLicenseTypeId(1L);
        licenseTypeResponseDTO.setDescription("Vacation");
    }

    @Test
    public void testGetAllLicenseTypes_success() {
        List<LicenseType> licenseTypes = Arrays.asList(licenseType);
        List<LicenseTypeResponseDTO> responseDTOs = Arrays.asList(licenseTypeResponseDTO);

        when(licenseTypeRepository.findAll()).thenReturn(licenseTypes);
        when(licenseTypeMapper.toResponseDTOList(licenseTypes)).thenReturn(responseDTOs);

        List<LicenseTypeResponseDTO> result = licenseTypeService.getAllLicenseTypes();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Vacation", result.get(0).getDescription());
    }

    @Test
    public void testGetLicenseTypeById_success() {
        when(licenseTypeRepository.findById(1L)).thenReturn(Optional.of(licenseType));
        when(licenseTypeMapper.toResponseDTO(licenseType)).thenReturn(licenseTypeResponseDTO);

        LicenseTypeResponseDTO result = licenseTypeService.getLicenseTypeById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getLicenseTypeId());
        assertEquals("Vacation", result.getDescription());
    }

    @Test
    public void testGetLicenseTypeById_notFound() {
        when(licenseTypeRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(LicenseTypeNotFoundException.class, () -> {
            licenseTypeService.getLicenseTypeById(999L);
        });
    }
    
}
