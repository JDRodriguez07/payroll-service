package com.app.payroll_service.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app.payroll_service.models.License;

@Repository
public interface LicenseRepository extends JpaRepository<License, Long> {
    
    List<License> findByStatusIgnoreCase(String status);

    List<License> findByStatusAndEndDateBeforeOrEndDateEquals(
            String status,
            LocalDate before,
            LocalDate equals);

}
