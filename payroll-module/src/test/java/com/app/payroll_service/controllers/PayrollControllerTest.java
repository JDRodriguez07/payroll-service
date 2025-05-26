package com.app.payroll_service.controllers;

import com.app.payroll_service.dto.PayrollResponseDTO;
import com.app.payroll_service.models.Payroll;
import com.app.payroll_service.services.PayrollService;
import com.app.payroll_service.mapper.PayrollMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PayrollController.class)
class PayrollControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PayrollService payrollService;

    @MockBean
    private PayrollMapper payrollMapper;

    private Payroll payroll;
    private PayrollResponseDTO payrollDTO;

    @BeforeEach
    void setUp() {
        payroll = new Payroll();
        payroll.setPayrollId(1L);
      
        payrollDTO = new PayrollResponseDTO();
        payrollDTO.setPayrollId(1L);
        payrollDTO.setNetSalary(new BigDecimal(2000.00)); 
    }

    @Test
    void getAllPayrolls_shouldReturnList() throws Exception {
        when(payrollService.getAllPayrolls()).thenReturn(List.of(payroll));
        when(payrollMapper.toResponseDTOList(List.of(payroll))).thenReturn(List.of(payrollDTO));

        mockMvc.perform(get("/api/payrolls"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].payrollId").value(1L))
                .andExpect(jsonPath("$[0].netSalary").value(2000.00));
    }

    @Test
    void getPayrollById_shouldReturnOne() throws Exception {
        when(payrollService.getPayrollById(1L)).thenReturn(payroll);
        when(payrollMapper.toResponseDTO(payroll)).thenReturn(payrollDTO);

        mockMvc.perform(get("/api/payrolls/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.payrollId").value(1L))
                .andExpect(jsonPath("$.netSalary").value(2000.00));
    }

    @Test
    void generateMonthlyPayrolls_shouldReturnSuccessMessage() throws Exception {
        doNothing().when(payrollService).generateMonthlyPayrolls();

        mockMvc.perform(post("/api/payrolls/generate"))
                .andExpect(status().isOk())
                .andExpect(content().string("Monthly payrolls generated successfully."));
    }
}
