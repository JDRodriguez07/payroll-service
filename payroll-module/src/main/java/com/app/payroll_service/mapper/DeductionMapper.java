package com.app.payroll_service.mapper;

import com.app.payroll_service.dto.DeductionResponseDTO;
import com.app.payroll_service.models.PayrollDeductions;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeductionMapper {

    @Mapping(source = "deduction.deductionId", target = "deductionId")
    @Mapping(source = "deduction.deductionType.name", target = "name")
    @Mapping(source = "deduction.amount", target = "amount")
    DeductionResponseDTO toResponseDTO(PayrollDeductions payrollDeductions);

    List<DeductionResponseDTO> toResponseDTOListFromPayroll(List<PayrollDeductions> payrollDeductions);
}

