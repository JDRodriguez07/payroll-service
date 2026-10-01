package com.app.payroll_service.controllers;

import com.app.payroll_service.dto.LicenseResponseDTO;
import com.app.payroll_service.dto.RequestLicenseDTO;
import com.app.payroll_service.services.LicenseService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/licenses")
public class LicenseController {

    @Autowired
    private LicenseService licenseService;

    // POST /licenses
    @PostMapping
    public ResponseEntity<LicenseResponseDTO> requestLicense(@Valid @RequestBody RequestLicenseDTO dto) {
        LicenseResponseDTO createdLicense = licenseService.requestLicense(dto);
        return ResponseEntity.ok(createdLicense);
    }

    // PUT /licenses/{id}/approve
    @PutMapping("/{id}/approve")
    public ResponseEntity<LicenseResponseDTO> approveLicense(@PathVariable Long id) {
        LicenseResponseDTO approved = licenseService.approveLicense(id);
        return ResponseEntity.ok(approved);
    }

    // PUT /licenses/{id}/reject
    @PutMapping("/{id}/reject")
    public ResponseEntity<LicenseResponseDTO> rejectLicense(@PathVariable Long id) {
        LicenseResponseDTO rejected = licenseService.rejectLicense(id);
        return ResponseEntity.ok(rejected);
    }

}
