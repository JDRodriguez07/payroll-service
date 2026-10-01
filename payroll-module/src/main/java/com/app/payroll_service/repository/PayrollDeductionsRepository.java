package com.app.payroll_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.payroll_service.models.Payroll;
import com.app.payroll_service.models.PayrollDeductions;

/**
 * Repository interface for accessing PayrollDeductions entities from the
 * database.
 * Provides standard CRUD operations and custom query methods.
 */
@Repository
public interface PayrollDeductionsRepository extends JpaRepository<PayrollDeductions, Long> {

    /**
     * Retrieves all deductions associated with a specific payroll.
     *
     * @param payroll the Payroll entity
     * @return list of PayrollDeductions linked to the given payroll
     */
    List<PayrollDeductions> findByPayroll(Payroll payroll);
}
