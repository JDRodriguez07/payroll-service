package com.app.payroll_service.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.app.payroll_service.dto.ScheduleResponseDTO;
import com.app.payroll_service.exceptions.ScheduleNotFoundException;
import com.app.payroll_service.mapper.ScheduleMapper;
import com.app.payroll_service.models.Schedule;
import com.app.payroll_service.repository.ScheduleRepository;
import com.app.payroll_service.services.ScheduleService;

@ExtendWith(MockitoExtension.class)
public class ScheduleServiceTest {

    @InjectMocks
    private ScheduleService scheduleService;

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private ScheduleMapper scheduleMapper;

    @Test
    void getAllSchedules_ReturnsMappedList() {
        Schedule schedule = new Schedule();
        schedule.setScheduleId(1L);
        List<Schedule> schedules = List.of(schedule);

        when(scheduleRepository.findAll()).thenReturn(schedules);

        ScheduleResponseDTO dto = new ScheduleResponseDTO();
        when(scheduleMapper.toResponseDTOList(schedules)).thenReturn(List.of(dto));

        List<ScheduleResponseDTO> result = scheduleService.getAllSchedules();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertSame(dto, result.get(0));

        verify(scheduleRepository).findAll();
        verify(scheduleMapper).toResponseDTOList(schedules);
    }

    @Test
    void getScheduleById_ReturnsDto_WhenFound() {
        Schedule schedule = new Schedule();
        schedule.setScheduleId(1L);
        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));

        ScheduleResponseDTO dto = new ScheduleResponseDTO();
        when(scheduleMapper.toResponseDTO(schedule)).thenReturn(dto);

        ScheduleResponseDTO result = scheduleService.getScheduleById(1L);

        assertNotNull(result);
        assertSame(dto, result);

        verify(scheduleRepository).findById(1L);
        verify(scheduleMapper).toResponseDTO(schedule);
    }

    @Test
    void getScheduleById_ThrowsException_WhenNotFound() {
        when(scheduleRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ScheduleNotFoundException.class, () -> scheduleService.getScheduleById(99L));

        verify(scheduleRepository).findById(99L);
        verifyNoMoreInteractions(scheduleMapper);
    }
}

