package com.app.payroll_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.app.payroll_service.dto.ContractResponseDTO;
import com.app.payroll_service.dto.CreateContractDTO;
import com.app.payroll_service.models.Contract;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ContractMapper {

    Contract toEntityContract(CreateContractDTO contractDTO);

    @Mapping(source = "contractType.name", target = "contractTypeName")
    @Mapping(source = "schedule.startTime", target = "startTime")
    @Mapping(source = "schedule.endTime", target = "endTime")
    @Mapping(source = "dailyHours", target = "dailyHours")
    ContractResponseDTO toResponseDTO(Contract contract);
}
