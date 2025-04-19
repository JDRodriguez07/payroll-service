package com.app.payroll_service.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.payroll_service.exceptions.PayrollDeductionsNotFoundException;
import com.app.payroll_service.models.PayrollDeductions;
import com.app.payroll_service.repository.PayrollDeductionsRepository;

@Service
public class PayrollDeductionsService {

    @Autowired
    private PayrollDeductionsRepository payrollDeductionsRepository;

    public List<PayrollDeductions> getAllPayrollDeductions() {
        return payrollDeductionsRepository.findAll();
    }

    public PayrollDeductions getPayrollDeductionsById(Long id) {
        return payrollDeductionsRepository.findById(id)
                .orElseThrow(() -> new PayrollDeductionsNotFoundException(id));
    }

    public PayrollDeductions createPayrollDeduction(PayrollDeductions payrollDeduction) {
        return payrollDeductionsRepository.save(payrollDeduction);
    }

    public void deletePayrollDeduction(Long id) {
        if (!payrollDeductionsRepository.existsById(id)) {
            throw new PayrollDeductionsNotFoundException(id);
        }
        payrollDeductionsRepository.deleteById(id);
    }

    public PayrollDeductions updatePayrollDeduction(Long id, PayrollDeductions updated) {
        PayrollDeductions existing = payrollDeductionsRepository.findById(id)
                .orElseThrow(() -> new PayrollDeductionsNotFoundException(id));

        existing.setPayroll(updated.getPayroll());
        existing.setDeduction(updated.getDeduction());

        return payrollDeductionsRepository.save(existing);
    }
}
