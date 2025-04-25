package com.app.payroll_service.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.payroll_service.exceptions.ContractNotFoundException;
import com.app.payroll_service.models.Contract;
import com.app.payroll_service.models.Deduction;
import com.app.payroll_service.models.DeductionType;
import com.app.payroll_service.models.Payroll;
import com.app.payroll_service.models.PayrollDeductions;
import com.app.payroll_service.repository.ContractRepository;
import com.app.payroll_service.repository.DeductionRepository;
import com.app.payroll_service.repository.DeductionTypeRepository;
import com.app.payroll_service.repository.PayrollDeductionsRepository;

@Service
public class PayrollDeductionsService {

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private DeductionTypeRepository deductionTypeRepository;

    @Autowired
    private DeductionRepository deductionRepository;

    @Autowired
    private PayrollDeductionsRepository payrollDeductionsRepository;

    /**
     * Generates deductions for the given payroll based on the employee's contract
     * salary
     * and deduction types. Links each deduction to the payroll.
     *
     * @param payroll Payroll to which the deductions will be applied
     * @return List of generated PayrollDeductions
     */
    /*public List<PayrollDeductions> generateDeductionsForPayroll(Payroll payroll) {
        List<PayrollDeductions> generatedDeductions = new ArrayList<>();

        // Fetch the contract using the userId from the payroll
        Contract contract = contractRepository.findByUserId(payroll.getUserId())
                .orElseThrow(() -> new ContractNotFoundException(payroll.getUserId()));

        BigDecimal salary = contract.getSalary(); // Base salary

        List<DeductionType> deductionTypes = deductionTypeRepository.findAll();

        for (DeductionType type : deductionTypes) {
            BigDecimal percentage = type.getPercentage(); // Ej: 0.04 = 4%
            BigDecimal amount = salary.multiply(percentage);

            // Create Deduction
            Deduction deduction = new Deduction();
            deduction.setDeductionType(type);
            deduction.setAmount(amount);
            deduction = deductionRepository.save(deduction);

            // Link it to payroll
            PayrollDeductions pd = new PayrollDeductions();
            pd.setPayroll(payroll);
            pd.setDeduction(deduction);
            pd = payrollDeductionsRepository.save(pd);

            generatedDeductions.add(pd);
        }

        return generatedDeductions;
    }

    /**
     * Retrieves all payroll-deduction associations from the database.
     *
     * @return List of PayrollDeductions
     */
    public List<PayrollDeductions> getAllPayrollDeductions() {
        return payrollDeductionsRepository.findAll();
    }

    /**
     * Deletes all deductions associated with a specific payroll.
     *
     * @param payroll The payroll whose deductions will be deleted
     */
    public void deleteDeductionsByPayroll(Payroll payroll) {
        List<PayrollDeductions> deductions = payrollDeductionsRepository.findByPayroll(payroll);
        payrollDeductionsRepository.deleteAll(deductions);
    }
}
