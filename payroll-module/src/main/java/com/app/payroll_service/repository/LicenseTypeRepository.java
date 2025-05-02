package com.app.payroll_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app.payroll_service.models.LicenseType;

/**
 * Repository interface for accessing LicenseType entities from the database.
 * Inherits standard CRUD operations from JpaRepository.
 */
@Repository
public interface LicenseTypeRepository extends JpaRepository<LicenseType, Long> {

}
