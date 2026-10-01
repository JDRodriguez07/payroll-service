package com.app.payroll_service.controllers;

import com.app.payroll_service.dto.LicenseTypeResponseDTO;
import com.app.payroll_service.services.LicenseTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing license types.
 * Provides endpoints to retrieve license type data.
 */
@RestController
@RequestMapping("/license-types")
public class LicenseTypeController {

    @Autowired
    private LicenseTypeService licenseTypeService;

    /**
     * Retrieves all license types.
     *
     * @return a list of LicenseTypeResponseDTOs
     */
    @GetMapping
    public ResponseEntity<List<LicenseTypeResponseDTO>> getAllLicenseTypes() {
        return ResponseEntity.ok(licenseTypeService.getAllLicenseTypes());
    }

    /**
     * Retrieves a specific license type by its ID.
     *
     * @param id the ID of the license type
     * @return the corresponding LicenseTypeResponseDTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<LicenseTypeResponseDTO> getLicenseTypeById(@PathVariable Long id) {
        return ResponseEntity.ok(licenseTypeService.getLicenseTypeById(id));
    }
}
