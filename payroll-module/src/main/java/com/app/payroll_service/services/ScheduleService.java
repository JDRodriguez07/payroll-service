package com.app.payroll_service.services;

import com.app.payroll_service.dto.ScheduleResponseDTO;
import com.app.payroll_service.exceptions.ScheduleNotFoundException;
import com.app.payroll_service.mapper.ScheduleMapper;
import com.app.payroll_service.models.Schedule;
import com.app.payroll_service.repository.ScheduleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ScheduleMapper scheduleMapper;

    public List<ScheduleResponseDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleRepository.findAll();
        return scheduleMapper.toResponseDTOList(schedules);
    }

    public ScheduleResponseDTO getScheduleById(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ScheduleNotFoundException(id));
        return scheduleMapper.toResponseDTO(schedule);
    }
}
