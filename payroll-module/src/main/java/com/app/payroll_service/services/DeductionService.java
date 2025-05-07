package com.app.payroll_service.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.payroll_service.dto.DeductionResponseDTO;
import com.app.payroll_service.exceptions.DeductionNotFoundException;

import com.app.payroll_service.mapper.DeductionMapper;
import com.app.payroll_service.models.PayrollDeductions;
import com.app.payroll_service.repository.DeductionRepository;
import com.app.payroll_service.repository.PayrollDeductionsRepository;

@Service
public class DeductionService {

    @Autowired
    private DeductionRepository deductionRepository;

    @Autowired
    private PayrollDeductionsRepository payrollDeductionsRepository;

    @Autowired
    private DeductionMapper deductionMapper;

    public List<DeductionResponseDTO> getAllDeductions() {
        List<PayrollDeductions> deductions = payrollDeductionsRepository.findAll();
        return deductionMapper.toResponseDTOListFromPayroll(deductions);

    }

    public DeductionResponseDTO getDeductionById(Long id) {
        PayrollDeductions deduction = payrollDeductionsRepository.findById(id)
                .orElseThrow(() -> new DeductionNotFoundException(id));

        return deductionMapper.toResponseDTO(deduction);
    }

}
