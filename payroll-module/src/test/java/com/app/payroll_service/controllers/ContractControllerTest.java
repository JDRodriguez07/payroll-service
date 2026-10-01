package com.app.payroll_service.controllers;

import com.app.payroll_service.controllers.ContractController;
import com.app.payroll_service.dto.ContractResponseDTO;
import com.app.payroll_service.dto.CreateContractDTO;
import com.app.payroll_service.dto.UpdateContractDTO;
import com.app.payroll_service.services.ContractService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ContractController.class)
class ContractControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContractService contractService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllContracts_shouldReturnList() throws Exception {
        ContractResponseDTO dto = new ContractResponseDTO();
        when(contractService.getAllContracts()).thenReturn(List.of(dto));

        mockMvc.perform(get("/contracts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void getContractById_shouldReturnContract() throws Exception {
        Long id = 1L;
        ContractResponseDTO dto = new ContractResponseDTO();
        when(contractService.getContractById(id)).thenReturn(dto);

        mockMvc.perform(get("/contracts/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    void createContract_shouldReturnCreatedContract() throws Exception {
        CreateContractDTO createDTO = new CreateContractDTO();
        createDTO.setSalary(new BigDecimal(5000.0));
        createDTO.setHireDate(LocalDate.of(2023, 1, 1));;
        createDTO.setContractTypeId(1L);
        createDTO.setTerminationDate(LocalDate.of(2023, 12, 31));
        createDTO.setScheduleId(1L);
        ContractResponseDTO responseDTO = new ContractResponseDTO();

        when(contractService.createContract(any(CreateContractDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/contracts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void updateContract_shouldReturnUpdatedContract() throws Exception {
        Long id = 1L;
        UpdateContractDTO updateDTO = new UpdateContractDTO();
        updateDTO.setSalary(new BigDecimal(5000.0));
        updateDTO.setHireDate(LocalDate.of(2023, 1, 1));

        ContractResponseDTO responseDTO = new ContractResponseDTO();
        when(contractService.updateContract(Mockito.eq(id), any(UpdateContractDTO.class)))
                .thenReturn(responseDTO);

        mockMvc.perform(put("/contracts/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void terminateContractManually_shouldReturnTerminatedContract() throws Exception {
        Long id = 1L;
        ContractResponseDTO responseDTO = new ContractResponseDTO();
        when(contractService.terminateContractManually(id)).thenReturn(responseDTO);

        mockMvc.perform(put("/contracts/{id}/terminate", id))
                .andExpect(status().isOk());
    }
}
