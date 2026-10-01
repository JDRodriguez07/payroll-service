package com.app.payroll_service.controllers;

import com.app.payroll_service.dto.ScheduleResponseDTO;
import com.app.payroll_service.services.ScheduleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing work schedules.
 * Provides endpoints to retrieve schedule information.
 */
@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    /**
     * Retrieves all schedules.
     *
     * @return list of ScheduleResponseDTOs
     */
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDTO>> getAllSchedules() {
        return ResponseEntity.ok(scheduleService.getAllSchedules());
    }

    /**
     * Retrieves a specific schedule by its ID.
     *
     * @param id the schedule ID
     * @return the corresponding ScheduleResponseDTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDTO> getScheduleById(@PathVariable Long id) {
        return ResponseEntity.ok(scheduleService.getScheduleById(id));
    }
}
