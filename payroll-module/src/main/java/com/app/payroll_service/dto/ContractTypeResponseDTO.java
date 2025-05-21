package com.app.payroll_service.dto;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for returning contract type details.
 */
@Data
public class ContractTypeResponseDTO {

    /**
     * Unique identifier of the contract type.
     */
    private Long contractTypeId;

    /**
     * Name or description of the contract type.
     */
    private String contractTypeName;
}
