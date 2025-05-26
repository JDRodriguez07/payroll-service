package com.app.payroll_service.controllers;

import com.app.payroll_service.dto.LicenseResponseDTO;
import com.app.payroll_service.dto.RequestLicenseDTO;
import com.app.payroll_service.services.LicenseService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LicenseController.class)
class LicenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LicenseService licenseService;

    @Autowired
    private ObjectMapper objectMapper;

    private LicenseResponseDTO licenseDTO;

    @BeforeEach
    void setUp() {
        licenseDTO = new LicenseResponseDTO();
        licenseDTO.setLicenseId(1L);
        licenseDTO.setStatus("PENDING");
        licenseDTO.setStartDate(LocalDate.of(2025, 6, 1));
        licenseDTO.setEndDate(LocalDate.of(2025, 6, 5));
    }

    @Test
    void getAllLicenses_shouldReturnList() throws Exception {
        List<LicenseResponseDTO> list = Arrays.asList(licenseDTO);
        when(licenseService.getAllLicenses()).thenReturn(list);

        mockMvc.perform(get("/licenses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].licenseId").value(1L));
    }

    @Test
    void getLicenseById_shouldReturnOne() throws Exception {
        when(licenseService.getLicenseById(1L)).thenReturn(licenseDTO);

        mockMvc.perform(get("/licenses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.licenseId").value(1L));
    }

    @Test
    void getAllPendingLicenses_shouldReturnList() throws Exception {
        when(licenseService.getAllPendingLicenses()).thenReturn(List.of(licenseDTO));

        mockMvc.perform(get("/licenses/pending"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllApprovedLicenses_shouldReturnList() throws Exception {
        when(licenseService.getAllApprovedLicenses()).thenReturn(List.of(licenseDTO));

        mockMvc.perform(get("/licenses/approved"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllRejectedLicenses_shouldReturnList() throws Exception {
        when(licenseService.getAllRejectedLicenses()).thenReturn(List.of(licenseDTO));

        mockMvc.perform(get("/licenses/rejected"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllCanceledLicenses_shouldReturnList() throws Exception {
        when(licenseService.getAllCanceledLicenses()).thenReturn(List.of(licenseDTO));

        mockMvc.perform(get("/licenses/canceled"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllTerminatedLicenses_shouldReturnList() throws Exception {
        when(licenseService.getAllTerminatedLicenses()).thenReturn(List.of(licenseDTO));

        mockMvc.perform(get("/licenses/terminated"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllActiveLicenses_shouldReturnList() throws Exception {
        when(licenseService.getAllActiveLicenses()).thenReturn(List.of(licenseDTO));

        mockMvc.perform(get("/licenses/active"))
                .andExpect(status().isOk());
    }

    @Test
    void requestLicense_shouldCreateLicense() throws Exception {
        RequestLicenseDTO request = new RequestLicenseDTO();
        request.setLicenseTypeId(1L);
        request.setUserId(1L);
        request.setStartDate(LocalDate.of(2025, 6, 1));
        request.setEndDate(LocalDate.of(2025, 6, 5));
        request.setLicenseTypeId(1L);
        LicenseResponseDTO licenseDTO = new LicenseResponseDTO();
        licenseDTO.setLicenseId(1L);
        when(licenseService.requestLicense(any(RequestLicenseDTO.class))).thenReturn(licenseDTO);

        mockMvc.perform(post("/licenses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.licenseId").value(1L));
    }

    @Test
    void approveLicense_shouldReturnApproved() throws Exception {
        when(licenseService.approveLicense(1L)).thenReturn(licenseDTO);

        mockMvc.perform(put("/licenses/1/approve"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.licenseId").value(1L));
    }

    @Test
    void rejectLicense_shouldReturnRejected() throws Exception {
        when(licenseService.rejectLicense(1L)).thenReturn(licenseDTO);

        mockMvc.perform(put("/licenses/1/reject"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.licenseId").value(1L));
    }

    @Test
    void cancelLicense_shouldReturnCanceled() throws Exception {
        when(licenseService.cancelLicense(1L)).thenReturn(licenseDTO);

        mockMvc.perform(put("/licenses/1/cancel"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.licenseId").value(1L));
    }
}
