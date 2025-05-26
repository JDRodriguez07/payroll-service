package com.app.payroll_service.controllers;

import com.app.payroll_service.dto.DeductionTypeResponseDTO;
import com.app.payroll_service.services.DeductionTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DeductionTypeController.class)
class DeductionTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeductionTypeService deductionTypeService;

    private DeductionTypeResponseDTO deductionType1;
    private DeductionTypeResponseDTO deductionType2;

    @BeforeEach
    void setUp() {
        deductionType1 = new DeductionTypeResponseDTO();
        deductionType1.setDeductionTypeId(1L);
        deductionType1.setName("AFP");

        deductionType2 = new DeductionTypeResponseDTO();
        deductionType2.setDeductionTypeId(2L);
        deductionType2.setName("Health");
    }

    @Test
    void getAllDeductionTypes_shouldReturnList() throws Exception {
        List<DeductionTypeResponseDTO> list = Arrays.asList(deductionType1, deductionType2);
        when(deductionTypeService.getAllDeductionTypes()).thenReturn(list);

        mockMvc.perform(get("/deduction-types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0]deductionTypeId").value(1L))
                .andExpect(jsonPath("$[0].name").value("AFP"))
                .andExpect(jsonPath("$[1].deductionTypeId").value(2L))
                .andExpect(jsonPath("$[1].name").value("Health"));
    }

    @Test
    void getDeductionTypeById_shouldReturnOneDeductionType() throws Exception {
        Long id = 1L;
        when(deductionTypeService.getDeductionTypeById(id)).thenReturn(deductionType1);

        mockMvc.perform(get("/deduction-types/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deductionTypeId").value(1L))
                .andExpect(jsonPath("$.name").value("AFP"));
    }
}
