package com.app.payroll_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app.payroll_service.models.ContractType;

/**
 * Repository interface for accessing ContractType entities from the database.
 * Inherits standard CRUD operations from JpaRepository.
 */
@Repository
public interface ContractTypeRepository extends JpaRepository<ContractType, Long> {

}
