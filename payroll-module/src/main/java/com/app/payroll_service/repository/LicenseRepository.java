package com.app.payroll_service.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app.payroll_service.models.License;

/**
 * Repository interface for accessing License entities from the database.
 * Provides standard CRUD operations and custom query methods.
 */
@Repository
public interface LicenseRepository extends JpaRepository<License, Long> {

    /**
     * Retrieves a list of licenses by status, ignoring case sensitivity.
     *
     * @param status the license status to filter by (e.g., "APROBADA", "PENDIENTE")
     * @return list of licenses matching the given status
     */
    List<License> findByStatusIgnoreCase(String status);

    /**
     * Retrieves a list of licenses that match the given status and have an end date
     * either before or exactly equal to the specified dates.
     *
     * @param status the license status to match
     * @param before end date must be before this date
     * @param equals or equal to this specific date
     * @return list of licenses matching the criteria
     */
    List<License> findByStatusAndEndDateBeforeOrEndDateEquals(
            String status,
            LocalDate before,
            LocalDate equals);

}
