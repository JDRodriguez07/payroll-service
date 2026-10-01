package com.app.payroll_service.mapper;

import com.app.payroll_service.dto.PayrollResponseDTO;
import com.app.payroll_service.models.Payroll;

import java.util.List;

import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = { DeductionMapper.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PayrollMapper {

    @Mapping(source = "payrollId", target = "payrollId")
    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "paidDays", target = "paidDays")
    @Mapping(source = "initialPeriod", target = "initialPeriod")
    @Mapping(source = "finalPeriod", target = "finalPeriod")
    @Mapping(source = "netSalary", target = "netSalary")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "payrollDeductions", target = "payrollDeductions")
    PayrollResponseDTO toResponseDTO(Payroll payroll);

    List<PayrollResponseDTO> toResponseDTOList(List<Payroll> payrolls);
}
