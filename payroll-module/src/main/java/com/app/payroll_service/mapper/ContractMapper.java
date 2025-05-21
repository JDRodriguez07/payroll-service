package com.app.payroll_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.app.payroll_service.dto.ContractResponseDTO;
import com.app.payroll_service.dto.CreateContractDTO;
import com.app.payroll_service.models.Contract;

/**
 * Mapper interface for converting between Contract entities and DTOs.
 * Uses MapStruct for automatic implementation generation.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ContractMapper {

    /**
     * Converts a CreateContractDTO into a Contract entity.
     *
     * @param contractDTO the DTO containing contract creation data
     * @return a Contract entity
     */
    Contract toEntityContract(CreateContractDTO contractDTO);

    /**
     * Converts a Contract entity into a ContractResponseDTO.
     * Custom mappings are defined to extract nested properties.
     *
     * @param contract the contract entity to convert
     * @return a populated ContractResponseDTO
     */
    @Mapping(source = "contractType.name", target = "contractTypeName")
    @Mapping(source = "schedule.startTime", target = "startTime")
    @Mapping(source = "schedule.endTime", target = "endTime")
    @Mapping(source = "dailyHours", target = "dailyHours")
    ContractResponseDTO toResponseDTO(Contract contract);
}
