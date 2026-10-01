package com.app.payroll_service.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.payroll_service.exceptions.LicenseTypeNotFoundException;
import com.app.payroll_service.models.LicenseType;
import com.app.payroll_service.repository.LicenseTypeRepository;

@Service
public class LicenseTypeService {

    @Autowired
    private LicenseTypeRepository licenseTypeRepository;

    public List<LicenseType> getAllLicenseTypes() {
        return licenseTypeRepository.findAll();
    }

    public LicenseType getLicenseTypeById(Long id) {
        return licenseTypeRepository.findById(id)
                .orElseThrow(() -> new LicenseTypeNotFoundException(id));
    }

    public LicenseType createLicenseType(LicenseType licenseType) {
        return licenseTypeRepository.save(licenseType);
    }

    public void deleteLicenseType(Long id) {
        if (!licenseTypeRepository.existsById(id)) {
            throw new LicenseTypeNotFoundException(id);
        }
        licenseTypeRepository.deleteById(id);
    }

    public LicenseType updateLicenseType(Long id, LicenseType updated) {
        LicenseType existing = licenseTypeRepository.findById(id)
                .orElseThrow(() -> new LicenseTypeNotFoundException(id));

        existing.setDescription(updated.getDescription());
        existing.setDiscount(updated.isDiscount());

        return licenseTypeRepository.save(existing);
    }
}
