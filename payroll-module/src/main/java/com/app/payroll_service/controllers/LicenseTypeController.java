package com.app.payroll_service.controllers;

import com.app.payroll_service.dto.LicenseTypeResponseDTO;
import com.app.payroll_service.services.LicenseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/license-types")
public class LicenseTypeController {

    @Autowired
    private LicenseTypeService licenseTypeService;

    @GetMapping
    public ResponseEntity<List<LicenseTypeResponseDTO>> getAllLicenseTypes() {
        return ResponseEntity.ok(licenseTypeService.getAllLicenseTypes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LicenseTypeResponseDTO> getLicenseTypeById(@PathVariable Long id) {
        return ResponseEntity.ok(licenseTypeService.getLicenseTypeById(id));
    }
}
