package com.app.payroll_service.controllers;

import com.app.payroll_service.dto.ContractResponseDTO;
import com.app.payroll_service.dto.CreateContractDTO;
import com.app.payroll_service.dto.UpdateContractDTO;
import com.app.payroll_service.services.ContractService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing contracts.
 * Provides endpoints to create, update, retrieve, and terminate contracts.
 */
@RestController
@RequestMapping("/contracts")
public class ContractController {

    @Autowired
    private ContractService contractService;

    /**
     * Retrieves all contracts.
     *
     * @return list of ContractResponseDTOs
     */
    @GetMapping
    public ResponseEntity<List<ContractResponseDTO>> getAllContracts() {
        return ResponseEntity.ok(contractService.getAllContracts());
    }

    /**
     * Retrieves a specific contract by its ID.
     *
     * @param id the ID of the contract
     * @return the corresponding ContractResponseDTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<ContractResponseDTO> getContractById(@PathVariable Long id) {
        return ResponseEntity.ok(contractService.getContractById(id));
    }

    /**
     * Creates a new contract.
     *
     * @param dto the contract creation data
     * @return the created ContractResponseDTO
     */
    @PostMapping
    public ResponseEntity<ContractResponseDTO> createContract(@Valid @RequestBody CreateContractDTO dto) {
        ContractResponseDTO created = contractService.createContract(dto);
        return ResponseEntity.ok(created);
    }

    /**
     * Updates an existing contract.
     *
     * @param id  the ID of the contract to update
     * @param dto the update data
     * @return the updated ContractResponseDTO
     */
    @PutMapping("/{id}")
    public ResponseEntity<ContractResponseDTO> updateContract(
            @PathVariable Long id,
            @Valid @RequestBody UpdateContractDTO dto) {
        ContractResponseDTO updated = contractService.updateContract(id, dto);
        return ResponseEntity.ok(updated);
    }

    /**
     * Manually terminates an active contract.
     *
     * @param id the ID of the contract to terminate
     * @return the terminated ContractResponseDTO
     */
    @PutMapping("/{id}/terminate")
    public ResponseEntity<ContractResponseDTO> terminateContractManually(@PathVariable Long id) {
        ContractResponseDTO terminated = contractService.terminateContractManually(id);
        return ResponseEntity.ok(terminated);
    }
    
}
