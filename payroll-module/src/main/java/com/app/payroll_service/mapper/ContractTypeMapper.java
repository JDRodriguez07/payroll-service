package com.app.payroll_service.mapper;

import com.app.payroll_service.dto.ContractTypeResponseDTO;
import com.app.payroll_service.models.ContractType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ContractTypeMapper {

    @Mapping(source = "name", target = "contractTypeName")
    ContractTypeResponseDTO toResponseDTO(ContractType contractType);

    List<ContractTypeResponseDTO> toResponseDTOList(List<ContractType> contractTypes);
}
