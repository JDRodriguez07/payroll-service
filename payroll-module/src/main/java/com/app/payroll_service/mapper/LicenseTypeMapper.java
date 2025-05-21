package com.app.payroll_service.mapper;

import com.app.payroll_service.dto.LicenseTypeResponseDTO;
import com.app.payroll_service.models.LicenseType;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper interface for converting LicenseType entities to their corresponding
 * DTOs.
 * Uses MapStruct for automatic implementation generation.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LicenseTypeMapper {

    /**
     * Converts a LicenseType entity to a LicenseTypeResponseDTO.
     *
     * @param licenseType the LicenseType entity
     * @return the corresponding DTO
     */
    LicenseTypeResponseDTO toResponseDTO(LicenseType licenseType);

    /**
     * Converts a list of LicenseType entities to a list of LicenseTypeResponseDTOs.
     *
     * @param licenseTypes the list of LicenseType entities
     * @return list of DTOs
     */
    List<LicenseTypeResponseDTO> toResponseDTOList(List<LicenseType> licenseTypes);
    
}
