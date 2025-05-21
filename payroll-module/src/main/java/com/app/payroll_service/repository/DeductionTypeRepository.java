package com.app.payroll_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app.payroll_service.models.DeductionType;

/**
 * Repository interface for accessing DeductionType entities from the database.
 * Provides standard CRUD operations and a method to find types by name
 * (case-insensitive).
 */
@Repository
public interface DeductionTypeRepository extends JpaRepository<DeductionType, Long> {

    /**
     * Retrieves a deduction type by its name, ignoring case sensitivity.
     *
     * @param name the name of the deduction type
     * @return an Optional containing the DeductionType if found, or empty if not
     *         found
     */
    Optional<DeductionType> findByNameIgnoreCase(String name);
    
}
