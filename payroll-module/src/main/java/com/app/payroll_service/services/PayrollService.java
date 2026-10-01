package com.app.payroll_service.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.payroll_service.exceptions.ContractNotFoundException;
import com.app.payroll_service.exceptions.PayrollNotFoundException;
import com.app.payroll_service.models.Contract;
import com.app.payroll_service.models.Payroll;
import com.app.payroll_service.models.PayrollDeductions;
import com.app.payroll_service.repository.ContractRepository;
import com.app.payroll_service.repository.PayrollRepository;

@Service
public class PayrollService {

    @Autowired
    private PayrollRepository payrollRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private PayrollDeductionsService payrollDeductionsService;

    /**
     * Retrieves all payroll records.
     */
    public List<Payroll> getAllPayrolls() {
        return payrollRepository.findAll();
    }

    /**
     * Retrieves a specific payroll by ID.
     *
     * @param id Payroll ID
     * @return Payroll
     * @throws PayrollNotFoundException if not found
     */
    public Payroll getPayrollById(Long id) {
        return payrollRepository.findById(id)
                .orElseThrow(() -> new PayrollNotFoundException(id));
    }

    /**
     * Creates a new payroll, generates its deductions, and calculates net salary.
     *
     * @param payroll Payroll to create
     * @return Created payroll with deductions and net salary
     */
    public Payroll createPayroll(Payroll payroll) {
        // Step 1: Save payroll to generate its ID
        Payroll savedPayroll = payrollRepository.save(payroll);

        // Step 2: Get contract and salary based on userId
        Contract contract = contractRepository.findByUserId(savedPayroll.getUserId())
                .orElseThrow(() -> new ContractNotFoundException(savedPayroll.getUserId()));

        BigDecimal salary = contract.getSalary();

        // Step 3: Generate deductions and calculate total
        List<PayrollDeductions> deductions = payrollDeductionsService.generateDeductionsForPayroll(savedPayroll);

        BigDecimal totalDeductions = deductions.stream()
                .map(pd -> pd.getDeduction().getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Step 4: Set net salary and associate deductions
        BigDecimal netSalary = salary.subtract(totalDeductions);
        savedPayroll.setNetSalary(netSalary);
        savedPayroll.setPayrollDeductions(deductions);

        // Step 5: Save updated payroll
        return payrollRepository.save(savedPayroll);
    }

    /**
     * Updates an existing payroll.
     *
     * @param id      Payroll ID
     * @param updated Updated payroll
     * @return Updated payroll
     */
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

    /**
     * Deletes a payroll and all its deductions.
     *
     * @param id Payroll ID
     */
    public void deletePayroll(Long id) {
        Payroll payroll = payrollRepository.findById(id)
                .orElseThrow(() -> new PayrollNotFoundException(id));

        payrollDeductionsService.deleteDeductionsByPayroll(payroll);
        payrollRepository.delete(payroll);
    }
}
