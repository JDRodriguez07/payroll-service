package com.app.payroll_service.mapper;

import com.app.payroll_service.dto.ScheduleResponseDTO;
import com.app.payroll_service.models.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ScheduleMapper {

    ScheduleResponseDTO toResponseDTO(Schedule schedule);

    List<ScheduleResponseDTO> toResponseDTOList(List<Schedule> schedules);
}
