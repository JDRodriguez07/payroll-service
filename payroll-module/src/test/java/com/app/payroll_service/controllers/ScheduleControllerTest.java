package com.app.payroll_service.controllers;

import com.app.payroll_service.dto.ScheduleResponseDTO;
import com.app.payroll_service.services.ScheduleService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ScheduleController.class)
class ScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ScheduleService scheduleService;

    private ScheduleResponseDTO scheduleDTO;

    @BeforeEach
    void setUp() {
        scheduleDTO = new ScheduleResponseDTO();
        scheduleDTO.setScheduleId(1L);
        scheduleDTO.setStartTime(LocalTime.of(8, 0));
        scheduleDTO.setEndTime(LocalTime.of(16, 0));
    }

    @Test
    void getAllSchedules_shouldReturnList() throws Exception {
        when(scheduleService.getAllSchedules()).thenReturn(List.of(scheduleDTO));

        mockMvc.perform(get("/schedules"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].scheduleId").value(1L))
                .andExpect(jsonPath("$[0].startTime").value("08:00:00"))
                .andExpect(jsonPath("$[0].endTime").value("16:00:00"));
    }

    @Test
    void getScheduleById_shouldReturnOne() throws Exception {
        when(scheduleService.getScheduleById(1L)).thenReturn(scheduleDTO);

        mockMvc.perform(get("/schedules/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.scheduleId").value(1L))
                .andExpect(jsonPath("$.startTime").value("08:00:00"))
                .andExpect(jsonPath("$.endTime").value("16:00:00"));
    }
}
