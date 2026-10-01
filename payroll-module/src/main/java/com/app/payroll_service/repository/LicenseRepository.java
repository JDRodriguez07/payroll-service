package com.app.payroll_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app.payroll_service.models.License;

@Repository
public interface LicenseRepository extends JpaRepository<License, Long> {

}
