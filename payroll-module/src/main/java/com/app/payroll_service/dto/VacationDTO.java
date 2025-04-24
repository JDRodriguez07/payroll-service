package com.app.payroll_service.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class VacationDTO {

    private Long vacationId;

    private Long userId;

    private LocalDate startDate;

    private LocalDate endDate;

    private int takenDays;

    private String status;

    public VacationDTO() {
    }

    public VacationDTO(Long vacationId, Long userId, LocalDate startDate, LocalDate endDate, 
                      int takenDays, String status) {
        this.vacationId = vacationId;
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.takenDays = takenDays;
        this.status = status;
    }


}
