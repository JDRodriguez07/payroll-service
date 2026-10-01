package com.app.payroll_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app.payroll_service.models.Deduction;

/**
 * Repository interface for accessing Deduction entities from the database.
 * Provides standard CRUD operations through JpaRepository.
 */
@Repository
public interface DeductionRepository extends JpaRepository<Deduction, Long> {

}
