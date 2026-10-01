package com.app.payroll_service.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.payroll_service.exceptions.PayrollNotFoundException;
import com.app.payroll_service.models.Payroll;
import com.app.payroll_service.models.PayrollDeductions;
import com.app.payroll_service.repository.PayrollRepository;

@Service
public class PayrollService {

    @Autowired
    private PayrollRepository payrollRepository;

    @Autowired
    private PayrollDeductionsService payrollDeductionsService;

    public List<Payroll> getAllPayrolls() {
        return payrollRepository.findAll();
    }

    public Payroll getPayrollById(Long id) {
        return payrollRepository.findById(id)
                .orElseThrow(() -> new PayrollNotFoundException(id));
    }

    public Payroll createPayroll(Payroll payroll) {
        // Guardar nómina
        Payroll savedPayroll = payrollRepository.save(payroll);

        // Generar y asociar deducciones
        List<PayrollDeductions> deductions = payrollDeductionsService.generateDeductionsForPayroll(savedPayroll);
        savedPayroll.setPayrollDeductions(deductions);

        return savedPayroll;
    }

    public Payroll updatePayroll(Long id, Payroll updated) {
        Payroll existing = payrollRepository.findById(id)
                .orElseThrow(() -> new PayrollNotFoundException(id));

        existing.setUserId(updated.getUserId());
        existing.setPaidDays(updated.getPaidDays());
        existing.setInitialPeriod(updated.getInitialPeriod());
        existing.setFinalPeriod(updated.getFinalPeriod());
        existing.setNetSalary(updated.getNetSalary());
        existing.setStatus(updated.getStatus());

        return payrollRepository.save(existing);
    }

    public void deletePayroll(Long id) {
        Payroll payroll = payrollRepository.findById(id)
                .orElseThrow(() -> new PayrollNotFoundException(id));

        payrollDeductionsService.deleteDeductionsByPayroll(payroll);
        payrollRepository.delete(payroll);
    }
}
