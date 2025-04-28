package com.app.payroll_service.mapper;

import com.app.payroll_service.dto.LicenseResponseDTO;
import com.app.payroll_service.dto.RequestLicenseDTO;
import com.app.payroll_service.models.License;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LicenseMapper {

    List<LicenseResponseDTO> toResponseDTOList(List<License> licenses);

    @Mapping(source = "licenseType.description", target = "licenseTypeDescription")
    @Mapping(target = "discountApplies", expression = "java(license.getLicenseType().isDiscount() ? \"Aplica\" : \"No aplica\")")
    LicenseResponseDTO toResponseDTO(License license);

    @Mapping(target = "licenseId", ignore = true)
    @Mapping(target = "licenseType", ignore = true) // se setea aparte en el service
    @Mapping(target = "days", ignore = true) // se calcula en el service
    @Mapping(target = "status", ignore = true) // se setea a "PENDIENTE"
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    License toEntity(RequestLicenseDTO dto);
}
