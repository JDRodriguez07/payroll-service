package com.app.payroll_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.app.payroll_service.dto.ContractResponseDTO;
import com.app.payroll_service.dto.CreateContractDTO;
import com.app.payroll_service.models.Contract;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ContractMapper {

    // Mapea DTO de entrada a entidad
    Contract toEntityContract(CreateContractDTO contractDTO);

    // Mapea entidad a DTO de respuesta, con campos personalizados
    @Mapping(source = "contractType.name", target = "contractTypeName")
    @Mapping(source = "schedule.startTime", target = "startTime")
    @Mapping(source = "schedule.endTime", target = "endTime")
    ContractResponseDTO toResponseDTO(Contract contract);
}
