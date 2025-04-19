package com.app.payroll_service.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.payroll_service.exceptions.PayrollNotFoundException;
import com.app.payroll_service.models.Payroll;
import com.app.payroll_service.repository.PayrollRepository;

@Service
public class PayrollService {

    @Autowired
    private PayrollRepository payrollRepository;

    public List<Payroll> getAllPayrolls() {
        return payrollRepository.findAll();
    }

    public Payroll getPayrollById(Long id) {
        return payrollRepository.findById(id)
                .orElseThrow(() -> new PayrollNotFoundException(id));
    }

    public Payroll createPayroll(Payroll payroll) {
        return payrollRepository.save(payroll);
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
        if (!payrollRepository.existsById(id)) {
            throw new PayrollNotFoundException(id);
        }
        payrollRepository.deleteById(id);
    }
}
