package com.app.payroll_service.repository;

import java.time.LocalDate;
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

    /**
     * Retrieves all vacation records that match the given status and have an end
     * date
     * less than or equal to the specified date.
     *
     * @param status the vacation status to filter by (e.g., APPROVED)
     * @param date   the date to compare against the vacation end date
     * @return a list of vacations matching the criteria
     */
    List<Vacation> findByStatusAndEndDateLessThanEqual(String status, LocalDate date);

}
