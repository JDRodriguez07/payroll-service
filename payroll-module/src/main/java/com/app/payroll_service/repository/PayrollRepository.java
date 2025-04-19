package com.app.payroll_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app.payroll_service.models.Payroll;

@Repository
public interface PayrollRepository extends JpaRepository<Payroll, Long> {

}
