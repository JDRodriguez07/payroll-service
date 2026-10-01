package com.app.payroll_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app.payroll_service.models.DeductionType;

@Repository
public interface DeductionTypeRepository extends JpaRepository<DeductionType, Long> {

}
