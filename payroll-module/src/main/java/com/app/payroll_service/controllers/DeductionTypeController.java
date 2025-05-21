package com.app.payroll_service.controllers;

import com.app.payroll_service.dto.DeductionTypeResponseDTO;
import com.app.payroll_service.services.DeductionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deduction-types")
public class DeductionTypeController {

    @Autowired
    private DeductionTypeService deductionTypeService;

    @GetMapping
    public ResponseEntity<List<DeductionTypeResponseDTO>> getAllDeductionTypes() {
        return ResponseEntity.ok(deductionTypeService.getAllDeductionTypes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeductionTypeResponseDTO> getDeductionTypeById(@PathVariable Long id) {
        return ResponseEntity.ok(deductionTypeService.getDeductionTypeById(id));
    }
}
