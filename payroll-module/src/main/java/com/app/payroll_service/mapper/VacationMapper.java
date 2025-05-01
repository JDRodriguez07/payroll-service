package com.app.payroll_service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.app.payroll_service.dto.RequestVacationDTO;
import com.app.payroll_service.dto.VacationResponseDTO;
import com.app.payroll_service.models.Vacation;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VacationMapper {

    List<VacationResponseDTO> toResponseDTOList(List<Vacation> vacations);

    VacationResponseDTO toResponseDTO(Vacation vacation);

    @Mapping(target = "vacationId", ignore = true)
    @Mapping(target = "takenDays", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Vacation toEntity(RequestVacationDTO dto);

}
