package com.app.payroll_service.controllers;

import com.app.payroll_service.dto.ContractTypeResponseDTO;
import com.app.payroll_service.services.ContractTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing contract types.
 * Provides endpoints to retrieve contract type data.
 */
@RestController
@RequestMapping("/contract-types")
public class ContractTypeController {

    @Autowired
    private ContractTypeService contractTypeService;

    /**
     * Retrieves all contract types.
     *
     * @return list of ContractTypeResponseDTOs
     */
    @GetMapping
    public ResponseEntity<List<ContractTypeResponseDTO>> getAllContractTypes() {
        return ResponseEntity.ok(contractTypeService.getAllContractTypes());
    }

    /**
     * Retrieves a specific contract type by its ID.
     *
     * @param id the ID of the contract type
     * @return the corresponding ContractTypeResponseDTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<ContractTypeResponseDTO> getContractTypeById(@PathVariable Long id) {
        return ResponseEntity.ok(contractTypeService.getContractTypeById(id));
    }
    
}
