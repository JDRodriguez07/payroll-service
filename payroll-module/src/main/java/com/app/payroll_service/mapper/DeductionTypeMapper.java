package com.app.payroll_service.mapper;

import com.app.payroll_service.dto.DeductionTypeResponseDTO;
import com.app.payroll_service.models.DeductionType;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeductionTypeMapper {

    DeductionTypeResponseDTO toResponseDTO(DeductionType deductionType);

    List<DeductionTypeResponseDTO> toResponseDTOList(List<DeductionType> deductionTypes);
}
