package com.app.payroll_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app.payroll_service.models.Payroll;

/**
 * Repository interface for accessing Payroll entities from the database.
 * Provides standard CRUD operations through JpaRepository.
 */
@Repository
public interface PayrollRepository extends JpaRepository<Payroll, Long> {

}
