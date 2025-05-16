package com.app.payroll_service.controllers;

import com.app.payroll_service.dto.LicenseResponseDTO;
import com.app.payroll_service.dto.RequestLicenseDTO;
import com.app.payroll_service.services.LicenseService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for handling license requests, approvals, rejections, and
 * cancellations.
 */
@RestController
@RequestMapping("/licenses")
public class LicenseController {

    @Autowired
    private LicenseService licenseService;

    /**
     * Retrieves all licenses in the system.
     *
     * @return a list of all licenses as response DTOs
     */
    @GetMapping
    public ResponseEntity<List<LicenseResponseDTO>> getAllLicenses() {
        return ResponseEntity.ok(licenseService.getAllLicenses());
    }

    /**
     * Retrieves a specific license by its ID.
     *
     * @param id the ID of the license
     * @return the license as a response DTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<LicenseResponseDTO> getLicenseById(@PathVariable Long id) {
        return ResponseEntity.ok(licenseService.getLicenseById(id));
    }

    /**
     * Retrieves all licenses with PENDING status.
     *
     * @return list of pending licenses as response DTOs
     */
    @GetMapping("/pending")
    public ResponseEntity<List<LicenseResponseDTO>> getAllPendingLicenses() {
        return ResponseEntity.ok(licenseService.getAllPendingLicenses());
    }

    /**
     * Retrieves all licenses with APPROVED status.
     *
     * @return list of approved licenses as response DTOs
     */
    @GetMapping("/approved")
    public ResponseEntity<List<LicenseResponseDTO>> getAllApprovedLicenses() {
        return ResponseEntity.ok(licenseService.getAllApprovedLicenses());
    }

    /**
     * Retrieves all licenses with REJECTED status.
     *
     * @return list of rejected licenses as response DTOs
     */
    @GetMapping("/rejected")
    public ResponseEntity<List<LicenseResponseDTO>> getAllRejectedLicenses() {
        return ResponseEntity.ok(licenseService.getAllRejectedLicenses());
    }

    /**
     * Retrieves all licenses with CANCELED status.
     *
     * @return list of canceled licenses as response DTOs
     */
    @GetMapping("/canceled")
    public ResponseEntity<List<LicenseResponseDTO>> getAllCanceledLicenses() {
        return ResponseEntity.ok(licenseService.getAllCanceledLicenses());
    }

    /**
     * Retrieves all licenses with TERMINATED status.
     *
     * @return list of terminated licenses as response DTOs
     */
    @GetMapping("/terminated")
    public ResponseEntity<List<LicenseResponseDTO>> getAllTerminatedLicenses() {
        return ResponseEntity.ok(licenseService.getAllTerminatedLicenses());
    }

    /**
     * Retrieves all licenses with ACTIVE status.
     *
     * @return list of active licenses as response DTOs
     */
    @GetMapping("/active")
    public ResponseEntity<List<LicenseResponseDTO>> getAllActiveLicenses() {
        return ResponseEntity.ok(licenseService.getAllActiveLicenses());
    }

    /**
     * Submits a new license request.
     *
     * @param dto the license request data
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
     * @param id the ID of the license to approve
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
     * @param id the ID of the license to reject
     * @return the rejected license as a response DTO
     */
    @PutMapping("/{id}/reject")
    public ResponseEntity<LicenseResponseDTO> rejectLicense(@PathVariable Long id) {
        LicenseResponseDTO rejected = licenseService.rejectLicense(id);
        return ResponseEntity.ok(rejected);
    }

    /**
     * Cancels a license that is in PENDING or APPROVED status.
     *
     * @param id the ID of the license to cancel
     * @return the canceled license as a response DTO
     */
    @PutMapping("/{id}/cancel")
    public ResponseEntity<LicenseResponseDTO> cancelLicense(@PathVariable Long id) {
        LicenseResponseDTO canceled = licenseService.cancelLicense(id);
        return ResponseEntity.ok(canceled);
    }

}
