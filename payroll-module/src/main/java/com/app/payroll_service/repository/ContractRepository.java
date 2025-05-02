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
     * Retrieves all contracts with the given status whose termination date is
     * less than or equal to the specified date.
     *
     * @param status the contract status to filter (e.g., ACTIVE)
     * @param date   the comparison date (typically LocalDate.now())
     * @return list of contracts matching the criteria
     */
    List<Contract> findByStatusAndTerminationDateLessThanEqual(String status, LocalDate date);

}
