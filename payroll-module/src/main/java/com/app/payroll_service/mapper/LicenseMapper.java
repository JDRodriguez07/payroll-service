package com.app.payroll_service.mapper;

import com.app.payroll_service.dto.LicenseResponseDTO;
import com.app.payroll_service.dto.RequestLicenseDTO;
import com.app.payroll_service.models.License;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper interface for converting License entities to DTOs and vice versa.
 * Utilizes MapStruct for automatic implementation.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LicenseMapper {

    /**
     * Converts a list of License entities into a list of LicenseResponseDTOs.
     *
     * @param licenses list of License entities
     * @return list of LicenseResponseDTOs
     */
    List<LicenseResponseDTO> toResponseDTOList(List<License> licenses);

    /**
     * Converts a License entity into a LicenseResponseDTO.
     * Includes a custom expression to determine if a discount applies.
     *
     * @param license the License entity
     * @return the corresponding LicenseResponseDTO
     */
    @Mapping(source = "licenseType.description", target = "licenseTypeDescription")
    @Mapping(target = "discountApplies", expression = "java(license.getLicenseType().isDiscount() ? \"Aplica\" : \"No aplica\")")
    LicenseResponseDTO toResponseDTO(License license);

    /**
     * Converts a RequestLicenseDTO into a License entity.
     * Some fields are ignored and will be set manually in the service layer:
     * - licenseType: set separately in the service
     * - days: calculated in the service
     * - status: set to "PENDIENTE"
     * - licenseId, createdAt, updatedAt: handled automatically
     *
     * @param dto the request DTO
     * @return the License entity
     */
    @Mapping(target = "licenseId", ignore = true)
    @Mapping(target = "licenseType", ignore = true) // set separately in the service
    @Mapping(target = "days", ignore = true) // calculated in the service
    @Mapping(target = "status", ignore = true) // initialized as "PENDIENTE"
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    License toEntity(RequestLicenseDTO dto);
}
