package com.app.payroll_service.services;

import com.app.payroll_service.dto.LicenseTypeResponseDTO;
import com.app.payroll_service.exceptions.LicenseTypeNotFoundException;
import com.app.payroll_service.mapper.LicenseTypeMapper;
import com.app.payroll_service.models.LicenseType;
import com.app.payroll_service.repository.LicenseTypeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for handling operations related to license types.
 */
@Service
public class LicenseTypeService {

    @Autowired
    private LicenseTypeRepository licenseTypeRepository;

    @Autowired
    private LicenseTypeMapper licenseTypeMapper;

    /**
     * Retrieves all license types from the database.
     *
     * @return a list of LicenseTypeResponseDTOs
     */
    public List<LicenseTypeResponseDTO> getAllLicenseTypes() {
        List<LicenseType> licenseTypes = licenseTypeRepository.findAll();
        return licenseTypeMapper.toResponseDTOList(licenseTypes);
    }

    /**
     * Retrieves a specific license type by its ID.
     *
     * @param id the ID of the license type
     * @return the corresponding LicenseTypeResponseDTO
     * @throws LicenseTypeNotFoundException if the license type is not found
     */
    public LicenseTypeResponseDTO getLicenseTypeById(Long id) {
        LicenseType licenseType = licenseTypeRepository.findById(id)
                .orElseThrow(() -> new LicenseTypeNotFoundException(id));
        return licenseTypeMapper.toResponseDTO(licenseType);
    }
    
}
