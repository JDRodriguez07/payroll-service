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
     * Retrieves all licenses with the given status whose end date is
     * less than or equal to the specified date.
     *
     * @param status the license status to filter (e.g., APPROVED)
     * @param date   the comparison date (typically LocalDate.now())
     * @return list of licenses matching the criteria
     */
    List<License> findByStatusAndEndDateLessThanEqual(String status, LocalDate date);

    List<License> findByStatusAndStartDate(String status, LocalDate startDate);

    List<License> findByStatusAndStartDateLessThanEqual(String status, LocalDate startDate);

}
