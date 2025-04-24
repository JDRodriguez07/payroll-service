package com.app.payroll_service.controllers;

import com.app.payroll_service.dto.CreateContractDTO;
import com.app.payroll_service.dto.ContractResponseDTO;
import com.app.payroll_service.services.ContractService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contracts")
public class ContractController {

    @Autowired
    private ContractService contractService;

    /**
     * Creates a new contract from a validated DTO request.
     *
     * @param dto Contract request data
     * @return Created contract response
     */
    @PostMapping
    public ResponseEntity<ContractResponseDTO> createContract(@Valid @RequestBody CreateContractDTO dto) {
        ContractResponseDTO created = contractService.createContract(dto);
        return ResponseEntity.ok(created);
    }
    
}
