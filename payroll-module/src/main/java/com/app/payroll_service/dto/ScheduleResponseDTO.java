package com.app.payroll_service.dto;

import java.time.LocalTime;
import lombok.Data;

@Data
public class ScheduleResponseDTO {
    private Long scheduleId;
    private LocalTime startTime;
    private LocalTime endTime;
}
