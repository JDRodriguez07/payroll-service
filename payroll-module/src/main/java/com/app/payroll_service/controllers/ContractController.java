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

@RestController
@RequestMapping("/contracts")
public class ContractController {

    @Autowired
    private ContractService contractService;

    // GET /contracts
    @GetMapping
    public ResponseEntity<List<ContractResponseDTO>> getAllContracts() {
        return ResponseEntity.ok(contractService.getAllContracts());
    }

    // GET /contracts/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ContractResponseDTO> getContractById(@PathVariable Long id) {
        return ResponseEntity.ok(contractService.getContractById(id));
    }

    // POST /contracts
    @PostMapping
    public ResponseEntity<ContractResponseDTO> createContract(@Valid @RequestBody CreateContractDTO dto) {
        ContractResponseDTO created = contractService.createContract(dto);
        return ResponseEntity.ok(created);
    }

    // PUT /contracts/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ContractResponseDTO> updateContract(@PathVariable Long id,
            @Valid @RequestBody UpdateContractDTO dto) {
        ContractResponseDTO updated = contractService.updateContract(id, dto);
        return ResponseEntity.ok(updated);
    }

    // PUT /contracts/{id}/terminate
    @PutMapping("/{id}/terminate")
    public ResponseEntity<ContractResponseDTO> terminateContract(@PathVariable Long id) {
        ContractResponseDTO terminated = contractService.terminateContract(id);
        return ResponseEntity.ok(terminated);
    }
}
