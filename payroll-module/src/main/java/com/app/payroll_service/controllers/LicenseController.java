package com.app.payroll_service.controllers;

import com.app.payroll_service.dto.LicenseResponseDTO;
import com.app.payroll_service.dto.RequestLicenseDTO;
import com.app.payroll_service.services.LicenseService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for handling license requests and approvals.
 */
@RestController
@RequestMapping("/licenses")
public class LicenseController {

    @Autowired
    private LicenseService licenseService;

    /**
     * Submits a new license request.
     *
     * @param dto the request data
     * @return the created license as a response DTO
     */
    @PostMapping
    public ResponseEntity<LicenseResponseDTO> requestLicense(@Valid @RequestBody RequestLicenseDTO dto) {
        LicenseResponseDTO createdLicense = licenseService.requestLicense(dto);
        return ResponseEntity.ok(createdLicense);
    }

    /**
     * Approves a pending license request by ID.
     *
     * @param id the license ID
     * @return the approved license as a response DTO
     */
    @PutMapping("/{id}/approve")
    public ResponseEntity<LicenseResponseDTO> approveLicense(@PathVariable Long id) {
        LicenseResponseDTO approved = licenseService.approveLicense(id);
        return ResponseEntity.ok(approved);
    }

    /**
     * Rejects a pending license request by ID.
     *
     * @param id the license ID
     * @return the rejected license as a response DTO
     */
    @PutMapping("/{id}/reject")
    public ResponseEntity<LicenseResponseDTO> rejectLicense(@PathVariable Long id) {
        LicenseResponseDTO rejected = licenseService.rejectLicense(id);
        return ResponseEntity.ok(rejected);
    }
    
}
