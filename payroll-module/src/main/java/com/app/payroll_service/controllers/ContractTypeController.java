package com.app.payroll_service.controllers;

import com.app.payroll_service.dto.ContractTypeResponseDTO;
import com.app.payroll_service.services.ContractTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contract-types")
public class ContractTypeController {

    @Autowired
    private ContractTypeService contractTypeService;

    @GetMapping
    public ResponseEntity<List<ContractTypeResponseDTO>> getAllContractTypes() {
        return ResponseEntity.ok(contractTypeService.getAllContractTypes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContractTypeResponseDTO> getContractTypeById(@PathVariable Long id) {
        return ResponseEntity.ok(contractTypeService.getContractTypeById(id));
    }
}
