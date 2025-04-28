package com.app.payroll_service.mapper;

import com.app.payroll_service.dto.LicenseTypeResponseDTO;
import com.app.payroll_service.models.LicenseType;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LicenseTypeMapper {

    LicenseTypeResponseDTO toResponseDTO(LicenseType licenseType);

    List<LicenseTypeResponseDTO> toResponseDTOList(List<LicenseType> licenseTypes);

}
