package com.app.payroll_service.controllers;

import com.app.payroll_service.dto.PayrollResponseDTO;
import com.app.payroll_service.models.Payroll;
import com.app.payroll_service.services.PayrollService;
import com.app.payroll_service.mapper.PayrollMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payrolls")
public class PayrollController {

    @Autowired
    private PayrollService payrollService;

    @Autowired
    private PayrollMapper payrollMapper;

    /**
     * Get all payrolls as DTOs.
     */
    @GetMapping
    public ResponseEntity<List<PayrollResponseDTO>> getAllPayrolls() {
        List<Payroll> payrolls = payrollService.getAllPayrolls();
        return ResponseEntity.ok(payrollMapper.toResponseDTOList(payrolls));
    }

    /**
     * Get a payroll by ID as DTO.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PayrollResponseDTO> getPayrollById(@PathVariable Long id) {
        Payroll payroll = payrollService.getPayrollById(id);
        return ResponseEntity.ok(payrollMapper.toResponseDTO(payroll));
    }

    /**
     * Generate payrolls for the current month.
     */
    @PostMapping("/generate")
    public ResponseEntity<String> generateMonthlyPayrolls() {
        payrollService.generateMonthlyPayrolls();
        return ResponseEntity.ok("Monthly payrolls generated successfully.");
    }
}
