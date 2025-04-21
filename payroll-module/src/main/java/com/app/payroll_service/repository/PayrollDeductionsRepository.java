package com.app.payroll_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.payroll_service.models.Payroll;
import com.app.payroll_service.models.PayrollDeductions;

@Repository
public interface PayrollDeductionsRepository extends JpaRepository<PayrollDeductions, Long> {

    List<PayrollDeductions> findByPayroll(Payroll payroll);

}
