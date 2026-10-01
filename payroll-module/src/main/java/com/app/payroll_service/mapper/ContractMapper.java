package com.app.payroll_service.mapper;

import org.mapstruct.Mapper;

import com.app.payroll_service.dto.CreateContractDTO;
import com.app.payroll_service.models.Contract;

@Mapper(componentModel = "spring")
public interface ContractMapper {
    
    Contract toEntityContract(CreateContractDTO contractDTO);

}
