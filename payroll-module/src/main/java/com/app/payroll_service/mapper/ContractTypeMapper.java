package com.app.payroll_service.mapper;

import com.app.payroll_service.dto.ContractTypeResponseDTO;
import com.app.payroll_service.models.ContractType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper interface for converting ContractType entities to their corresponding
 * DTOs.
 * Uses MapStruct for automatic implementation generation.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ContractTypeMapper {

    /**
     * Converts a ContractType entity into a ContractTypeResponseDTO.
     *
     * @param contractType the ContractType entity to convert
     * @return a populated ContractTypeResponseDTO
     */
    @Mapping(source = "name", target = "contractTypeName")
    ContractTypeResponseDTO toResponseDTO(ContractType contractType);

    /**
     * Converts a list of ContractType entities into a list of
     * ContractTypeResponseDTOs.
     *
     * @param contractTypes the list of entities to convert
     * @return a list of ContractTypeResponseDTOs
     */
    List<ContractTypeResponseDTO> toResponseDTOList(List<ContractType> contractTypes);
    
}
