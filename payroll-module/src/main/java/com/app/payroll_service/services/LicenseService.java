package com.app.payroll_service.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.payroll_service.exceptions.LicenseNotFoundException;
import com.app.payroll_service.models.License;
import com.app.payroll_service.repository.LicenseRepository;

@Service
public class LicenseService {

    @Autowired
    private LicenseRepository licenseRepository;

    public List<License> getAllLicenses() {
        return licenseRepository.findAll();
    }

    public License getLicenseById(Long id) {
        return licenseRepository.findById(id)
                .orElseThrow(() -> new LicenseNotFoundException(id));
    }

    public License createLicense(License license) {
        return licenseRepository.save(license);
    }

    public void deleteLicense(Long id) {
        if (!licenseRepository.existsById(id)) {
            throw new LicenseNotFoundException(id);
        }
        licenseRepository.deleteById(id);
    }

    public License updateLicense(Long id, License updatedLicense) {
        License existingLicense = licenseRepository.findById(id)
                .orElseThrow(() -> new LicenseNotFoundException(id));

        existingLicense.setLicenseType(updatedLicense.getLicenseType());
        existingLicense.setStartDate(updatedLicense.getStartDate());
        existingLicense.setEndDate(updatedLicense.getEndDate());
        existingLicense.setDays(updatedLicense.getDays());
        existingLicense.setStatus(updatedLicense.getStatus());

        return licenseRepository.save(existingLicense);
    }
}
