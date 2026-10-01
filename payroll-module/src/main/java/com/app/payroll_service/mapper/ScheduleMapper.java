package com.app.payroll_service.mapper;

import com.app.payroll_service.dto.ScheduleResponseDTO;
import com.app.payroll_service.models.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper interface for converting Schedule entities to their corresponding
 * DTOs.
 * Uses MapStruct for automatic implementation generation.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ScheduleMapper {

    /**
     * Converts a Schedule entity to a ScheduleResponseDTO.
     *
     * @param schedule the Schedule entity
     * @return the corresponding DTO
     */
    ScheduleResponseDTO toResponseDTO(Schedule schedule);

    /**
     * Converts a list of Schedule entities to a list of ScheduleResponseDTOs.
     *
     * @param schedules the list of Schedule entities
     * @return list of DTOs
     */
    List<ScheduleResponseDTO> toResponseDTOList(List<Schedule> schedules);
}
