package com.app.payroll_service.controllers;

import com.app.payroll_service.dto.RequestVacationDTO;
import com.app.payroll_service.dto.VacationResponseDTO;
import com.app.payroll_service.services.VacationService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for handling vacation requests and actions such as approval,
 * rejection, cancellation, and retrieval.
 */
@RestController
@RequestMapping("/vacations")
public class VacationController {

    @Autowired
    private VacationService vacationService;

    /**
     * Creates a new vacation request.
     *
     * @param dto the vacation request details
     * @return the created vacation as a response DTO
     */
    @PostMapping
    public ResponseEntity<VacationResponseDTO> requestVacation(@Valid @RequestBody RequestVacationDTO dto) {
        VacationResponseDTO created = vacationService.requestVacation(dto);
        return ResponseEntity.ok(created);
    }

    /**
     * Approves a vacation request by its ID.
     *
     * @param id the ID of the vacation to approve
     * @return the approved vacation as a response DTO
     */
    @PutMapping("/{id}/approve")
    public ResponseEntity<VacationResponseDTO> approveVacation(@PathVariable Long id) {
        VacationResponseDTO approved = vacationService.approveVacation(id);
        return ResponseEntity.ok(approved);
    }

    /**
     * Rejects a vacation request by its ID.
     *
     * @param id the ID of the vacation to reject
     * @return the rejected vacation as a response DTO
     */
    @PutMapping("/{id}/reject")
    public ResponseEntity<VacationResponseDTO> rejectVacation(@PathVariable Long id) {
        VacationResponseDTO rejected = vacationService.rejectVacation(id);
        return ResponseEntity.ok(rejected);
    }

    /**
     * Cancels a vacation request by its ID.
     *
     * @param id the ID of the vacation to cancel
     * @return the canceled vacation as a response DTO
     */
    @PutMapping("/{id}/cancel")
    public ResponseEntity<VacationResponseDTO> cancelVacation(@PathVariable Long id) {
        VacationResponseDTO canceled = vacationService.cancelVacation(id);
        return ResponseEntity.ok(canceled);
    }

    /**
     * Retrieves all vacation requests.
     *
     * @return a list of all vacations as response DTOs
     */
    @GetMapping
    public ResponseEntity<List<VacationResponseDTO>> getAllVacations() {
        return ResponseEntity.ok(vacationService.getAllVacations());
    }

    /**
     * Retrieves a specific vacation by ID.
     *
     * @param id the ID of the vacation
     * @return the corresponding vacation as a response DTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<VacationResponseDTO> getVacationById(@PathVariable Long id) {
        return ResponseEntity.ok(vacationService.getVacationById(id));
    }

    /**
     * Retrieves all vacation requests currently in PENDING status.
     *
     * @return a list of pending vacations as response DTOs
     */
    @GetMapping("/pending")
    public ResponseEntity<List<VacationResponseDTO>> getAllPendingVacations() {
        return ResponseEntity.ok(vacationService.getAllPendingVacations());
    }

    /**
     * Retrieves all vacation requests currently in APPROVED status.
     *
     * @return a list of approved vacations as response DTOs
     */
    @GetMapping("/active")
    public ResponseEntity<List<VacationResponseDTO>> getAllActiveVacations() {
        return ResponseEntity.ok(vacationService.getAllActiveVacations());
    }

    /**
     * Retrieves all vacation requests currently in ACTIVE status.
     *
     * @return a list of active vacations as response DTOs
     */
    @GetMapping("/approved")
    public ResponseEntity<List<VacationResponseDTO>> getAllApprovedVacations() {
        return ResponseEntity.ok(vacationService.getAllApprovedVacations());
    }

    /**
     * Retrieves all vacations with TERMINATED status.
     *
     * @return list of terminated vacations as response DTOs
     */
    @GetMapping("/terminated")
    public ResponseEntity<List<VacationResponseDTO>> getAllTerminatedVacations() {
        return ResponseEntity.ok(vacationService.getAllTerminatedVacations());
    }

    /**
     * Retrieves all vacations with REJECTED status.
     *
     * @return list of rejected vacations as response DTOs
     */
    @GetMapping("/rejected")
    public ResponseEntity<List<VacationResponseDTO>> getAllRejectedVacations() {
        return ResponseEntity.ok(vacationService.getAllRejectedVacations());
    }

    /**
     * Retrieves all vacations with CANCELED status.
     *
     * @return list of canceled vacations as response DTOs
     */
    @GetMapping("/canceled")
    public ResponseEntity<List<VacationResponseDTO>> getAllCanceledVacations() {
        return ResponseEntity.ok(vacationService.getAllCanceledVacations());
    }

}
