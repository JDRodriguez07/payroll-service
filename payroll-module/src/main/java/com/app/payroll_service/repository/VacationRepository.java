package com.app.payroll_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app.payroll_service.models.Vacation;

/**
 * Repository interface for accessing Vacation entities from the database.
 * Provides standard CRUD operations and custom query methods.
 */
@Repository
public interface VacationRepository extends JpaRepository<Vacation, Long> {

    /**
     * Retrieves a list of vacations filtered by status, ignoring case sensitivity.
     *
     * @param status the vacation status to match (e.g., "APROBADA", "PENDIENTE")
     * @return list of Vacation entities with the given status
     */
    List<Vacation> findByStatusIgnoreCase(String status);
    
}
