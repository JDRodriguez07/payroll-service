package com.app.payroll_service.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app.payroll_service.models.PayrollDeductions;
import com.app.payroll_service.repository.PayrollDeductionsRepository;

@Service
public class PayrollDeductionsService {

    @Autowired
    private PayrollDeductionsRepository payrollDeductionsRepository;

    /**
     * Retrieves all payroll-deduction associations from the database.
     *
     * @return List of PayrollDeductions
     */
    public List<PayrollDeductions> getAllPayrollDeductions() {
        return payrollDeductionsRepository.findAll();
    }
}
