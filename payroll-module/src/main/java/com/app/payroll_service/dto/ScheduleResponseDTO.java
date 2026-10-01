package com.app.payroll_service.dto;

import java.time.LocalTime;
import lombok.Data;

/**
 * Data Transfer Object (DTO) for returning schedule details.
 */
@Data
public class ScheduleResponseDTO {

    /**
     * Unique identifier of the schedule.
     */
    private Long scheduleId;

    /**
     * Time of day the schedule starts.
     */
    private LocalTime startTime;

    /**
     * Time of day the schedule ends.
     */
    private LocalTime endTime;
}
