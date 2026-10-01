package com.app.payroll_service.services;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.payroll_service.enums.PayrollStatusEnum;
import com.app.payroll_service.enums.ContractStatusEnum;
import com.app.payroll_service.dto.EmployeeAndContract;

import com.app.payroll_service.exceptions.IsNotLastWorkDayOfMonthException;
import com.app.payroll_service.exceptions.PayrollNotFoundException;
import com.app.payroll_service.models.Contract;
import com.app.payroll_service.models.Deduction;
import com.app.payroll_service.models.DeductionType;
import com.app.payroll_service.models.Payroll;
import com.app.payroll_service.models.PayrollDeductions;
import com.app.payroll_service.repository.ContractRepository;
import com.app.payroll_service.repository.DeductionRepository;
import com.app.payroll_service.repository.DeductionTypeRepository;
import com.app.payroll_service.repository.PayrollDeductionsRepository;
import com.app.payroll_service.repository.PayrollRepository;

@Service
public class PayrollService {

    @Autowired
    private PayrollRepository payrollRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private PayrollDeductionsRepository payrollDeductionsRepository;

    @Autowired
    private DeductionTypeRepository deductionTypeRepository;

    @Autowired
    private DeductionRepository deductionRepository;

    @Autowired
    private com.app.payroll_service.client.ApiClient apiClient;

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

    public void generateMonthlyPayrolls(String token) {
        List<EmployeeAndContract> employeesAndContracts = apiClient.allEmployeesAndContracts(token);
        System.out.println(employeesAndContracts);

        // Map contractId -> userId for easy access
        Map<Long, Long> contractUserMap = employeesAndContracts.stream()
                .collect(Collectors.toMap(EmployeeAndContract::getContractId, EmployeeAndContract::getEmployeeId));

        LocalDate today = LocalDate.now();

        if (!isLastBusinessDayOfMonth(today)) {
            throw new IsNotLastWorkDayOfMonthException();
        }

        LocalDate initialPeriod = today.withDayOfMonth(1);
        LocalDate finalPeriod = today.withDayOfMonth(today.lengthOfMonth());

        // Find or create the HEALTH deduction type
        DeductionType healthType = deductionTypeRepository.findByNameIgnoreCase("Salud")
                .orElseGet(() -> {
                    BigDecimal defaultPercentage = BigDecimal.valueOf(0.04);
                    DeductionType created = new DeductionType();
                    created.setName("Salud");
                    created.setPercentage(defaultPercentage);
                    return deductionTypeRepository.save(created);
                });

        DeductionType pensionType = deductionTypeRepository.findByNameIgnoreCase("Pension")
                .orElseGet(() -> {
                    BigDecimal defaultPercentage = BigDecimal.valueOf(0.04);
                    DeductionType created = new DeductionType();
                    created.setName("Pension");
                    created.setPercentage(defaultPercentage);
                    return deductionTypeRepository.save(created);
                });

        List<Contract> contracts = contractRepository.findByStatus(ContractStatusEnum.ACTIVE.getValue());

        for (Contract contract : contracts) {

            Long userId = contractUserMap.get(contract.getContractId());
            if (userId == null) {
                continue; // Skip contract with no associated user
            }

            BigDecimal grossSalary = contract.getSalary();
            BigDecimal healthDeductionAmount = grossSalary.multiply(healthType.getPercentage());
            BigDecimal pensionDeductionAmount = grossSalary.multiply(pensionType.getPercentage());
            BigDecimal totalDeductions = healthDeductionAmount.add(pensionDeductionAmount);
            BigDecimal netSalary = grossSalary.subtract(totalDeductions);

            Payroll payroll = new Payroll();
            payroll.setUserId(userId);
            payroll.setPaidDays(30);
            payroll.setInitialPeriod(initialPeriod);
            payroll.setFinalPeriod(finalPeriod);
            payroll.setNetSalary(netSalary);
            payroll.setStatus(PayrollStatusEnum.PAID.getValue());

            Payroll savedPayroll = payrollRepository.save(payroll);

            // Save deduction of HEALTH
            Deduction healthDeduction = new Deduction(healthType, healthDeductionAmount);
            healthDeduction = deductionRepository.save(healthDeduction);

            PayrollDeductions payrollHealthDeduction = new PayrollDeductions();
            payrollHealthDeduction.setPayroll(savedPayroll);
            payrollHealthDeduction.setDeduction(healthDeduction);
            payrollDeductionsRepository.save(payrollHealthDeduction);

            // Safe deduction of PENSION
            Deduction pensionDeduction = new Deduction(pensionType, pensionDeductionAmount);
            pensionDeduction = deductionRepository.save(pensionDeduction);

            PayrollDeductions payrollPensionDeduction = new PayrollDeductions();
            payrollPensionDeduction.setPayroll(savedPayroll);
            payrollPensionDeduction.setDeduction(pensionDeduction);
            payrollDeductionsRepository.save(payrollPensionDeduction);
        }

    }

    public static boolean isLastBusinessDayOfMonth(LocalDate today) {
        LocalDate lastDayOfMonth = today.with(TemporalAdjusters.lastDayOfMonth());

        if (lastDayOfMonth.getDayOfWeek() == DayOfWeek.SUNDAY) {
            lastDayOfMonth = lastDayOfMonth.minusDays(1);
        }

        return today.equals(lastDayOfMonth);
    }

}
