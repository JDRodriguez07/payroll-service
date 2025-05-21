package com.app.payroll_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app.payroll_service.models.Schedule;

/**
 * Repository interface for accessing Schedule entities from the database.
 * Inherits standard CRUD operations from JpaRepository.
 */
@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

}
