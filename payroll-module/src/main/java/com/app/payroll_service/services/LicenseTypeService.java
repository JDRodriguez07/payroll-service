package com.app.payroll_service.services;

import com.app.payroll_service.dto.LicenseTypeResponseDTO;
import com.app.payroll_service.exceptions.LicenseTypeNotFoundException;
import com.app.payroll_service.mapper.LicenseTypeMapper;
import com.app.payroll_service.models.LicenseType;
import com.app.payroll_service.repository.LicenseTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LicenseTypeService {

    @Autowired
    private LicenseTypeRepository licenseTypeRepository;

    @Autowired
    private LicenseTypeMapper licenseTypeMapper;

    public List<LicenseTypeResponseDTO> getAllLicenseTypes() {
        List<LicenseType> licenseTypes = licenseTypeRepository.findAll();
        return licenseTypeMapper.toResponseDTOList(licenseTypes);
    }

    public LicenseTypeResponseDTO getLicenseTypeById(Long id) {
        LicenseType licenseType = licenseTypeRepository.findById(id)
                .orElseThrow(() -> new LicenseTypeNotFoundException(id));
        return licenseTypeMapper.toResponseDTO(licenseType);
    }
}
