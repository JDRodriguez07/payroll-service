package com.app.payroll_service.controllers;

import com.app.payroll_service.dto.LicenseTypeResponseDTO;
import com.app.payroll_service.services.LicenseTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LicenseTypeController.class)
class LicenseTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LicenseTypeService licenseTypeService;

    private LicenseTypeResponseDTO licenseTypeDTO;

    @BeforeEach
    void setUp() {
        licenseTypeDTO = new LicenseTypeResponseDTO();
        licenseTypeDTO.setLicenseTypeId(1L);
        licenseTypeDTO.setDescription("Vacaciones");
    }

    @Test
    void getAllLicenseTypes_shouldReturnList() throws Exception {
        when(licenseTypeService.getAllLicenseTypes()).thenReturn(List.of(licenseTypeDTO));

        mockMvc.perform(get("/license-types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].licenseTypeId").value(1L))
                .andExpect(jsonPath("$[0].description").value("Vacaciones"));
    }

    @Test
    void getLicenseTypeById_shouldReturnOne() throws Exception {
        when(licenseTypeService.getLicenseTypeById(1L)).thenReturn(licenseTypeDTO);

        mockMvc.perform(get("/license-types/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.licenseTypeId").value(1L))
                .andExpect(jsonPath("$.description").value("Vacaciones"));
    }
}
