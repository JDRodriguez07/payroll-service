package com.app.payroll_service.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app.payroll_service.models.Contract;

/**
 * Repository interface for accessing Contract entities from the database.
 * Extends JpaRepository to provide standard CRUD operations.
 */
@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {

    /**
     * Retrieves a list of contracts that match the given status and have a
     * termination date
     * either before or exactly equal to the specified dates.
     *
     * @param status the contract status to match (e.g., ACTIVE, TERMINATED)
     * @param before the date before which contracts are considered
     * @param equals the exact termination date to include
     * @return list of contracts matching the criteria
     */
    List<Contract> findByStatusAndTerminationDateBeforeOrTerminationDateEquals(
            String status,
            LocalDate before,
            LocalDate equals);
            
}
