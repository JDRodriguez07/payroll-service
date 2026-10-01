package com.app.payroll_service.controllers;

import com.app.payroll_service.dto.ContractTypeResponseDTO;
import com.app.payroll_service.services.ContractTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ContractTypeController.class)
class ContractTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContractTypeService contractTypeService;

    private ContractTypeResponseDTO contractType1;
    private ContractTypeResponseDTO contractType2;

    @BeforeEach
    void setUp() {
        contractType1 = new ContractTypeResponseDTO();
        contractType1.setContractTypeId(1L);
        contractType1.setContractTypeName("Full Time");

        contractType2 = new ContractTypeResponseDTO();
        contractType2.setContractTypeId(2L);
        contractType2.setContractTypeName("Part Time");
    }

    @Test
    void getAllContractTypes_shouldReturnList() throws Exception {
        List<ContractTypeResponseDTO> list = Arrays.asList(contractType1, contractType2);
        when(contractTypeService.getAllContractTypes()).thenReturn(list);

        mockMvc.perform(get("/contract-types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].contractTypeId").value(1L))
                .andExpect(jsonPath("$[0].contractTypeName").value("Full Time"))
                .andExpect(jsonPath("$[1].contractTypeId").value(2L))
                .andExpect(jsonPath("$[1].contractTypeName").value("Part Time"));
    }

    @Test
    void getContractTypeById_shouldReturnOneContractType() throws Exception {
        Long id = 1L;
        when(contractTypeService.getContractTypeById(id)).thenReturn(contractType1);

        mockMvc.perform(get("/contract-types/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contractTypeId").value(1L))
                .andExpect(jsonPath("$.contractTypeName").value("Full Time"));
    }
}
