package com.app.payroll_service.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.payroll_service.dto.RequestVacationDTO;
import com.app.payroll_service.dto.VacationResponseDTO;
import com.app.payroll_service.enums.VacationStatusEnum;
import com.app.payroll_service.exceptions.InvalidVacationDatesException;
import com.app.payroll_service.exceptions.InvalidVacationDaysException;
import com.app.payroll_service.exceptions.VacationNotFoundException;
import com.app.payroll_service.exceptions.VacationStatusNotPendingApprovedException;
import com.app.payroll_service.mapper.VacationMapper;
import com.app.payroll_service.models.Vacation;
import com.app.payroll_service.repository.VacationRepository;

@Service
public class VacationService {

    @Autowired
    private VacationRepository vacationRepository;

    @Autowired
    private VacationMapper vacationMapper;

    public static final int VACATION_DAYS = 15;

    public List<VacationResponseDTO> getAllVacations() {
        List<Vacation> vacations = vacationRepository.findAll();
        return vacationMapper.toResponseDTOList(vacations);
    }

    public VacationResponseDTO getVacationById(Long id) {
        Vacation vacation = vacationRepository.findById(id)
                .orElseThrow(() -> new VacationNotFoundException(id));
        return vacationMapper.toResponseDTO(vacation);
    }

    public VacationResponseDTO requestVacation(RequestVacationDTO dto) {

        if (dto.getEndDate().isBefore(dto.getStartDate())) {
            throw new InvalidVacationDatesException();
        }

        int days = calculateWorkingDays(dto.getStartDate(), dto.getEndDate());

        if (days != VACATION_DAYS) {
            throw new InvalidVacationDaysException();
        }

        Vacation vacation = vacationMapper.toEntity(dto);

        // Note: User ID is assumed to be valid.
        // Validation is expected to be handled by the User microservice.
        vacation.setUserId(dto.getUserId());

        vacation.setTakenDays(days);
        vacation.setStatus(VacationStatusEnum.PENDING.getValue());

        Vacation savedVacation = vacationRepository.save(vacation);
        return vacationMapper.toResponseDTO(savedVacation);
    }

    public VacationResponseDTO approveVacation(Long vacationId) {
        Vacation vacation = vacationRepository.findById(vacationId)
                .orElseThrow(() -> new VacationNotFoundException(vacationId));

        if (!VacationStatusEnum.PENDING.getValue().equals(vacation.getStatus())) {
            throw new VacationStatusNotPendingApprovedException(vacationId);
        }

        vacation.setStatus(VacationStatusEnum.APPROVED.getValue());

        Vacation savedVacation = vacationRepository.save(vacation);
        return vacationMapper.toResponseDTO(savedVacation);
    }

    public VacationResponseDTO rejectVacation(Long vacationId) {
        Vacation vacation = vacationRepository.findById(vacationId)
                .orElseThrow(() -> new VacationNotFoundException(vacationId));

        if (!VacationStatusEnum.PENDING.getValue().equals(vacation.getStatus())) {
            throw new VacationStatusNotPendingApprovedException(vacationId);
        }

        vacation.setStatus(VacationStatusEnum.REJECTED.getValue());

        Vacation savedVacation = vacationRepository.save(vacation);
        return vacationMapper.toResponseDTO(savedVacation);
    }

    public VacationResponseDTO cancelVacation(Long vacationId) {
        Vacation vacation = vacationRepository.findById(vacationId)
                .orElseThrow(() -> new VacationNotFoundException(vacationId));

        if (!(VacationStatusEnum.APPROVED.getValue().equals(vacation.getStatus())
                || VacationStatusEnum.PENDING.getValue().equals(vacation.getStatus()))) {
            throw new VacationStatusNotPendingApprovedException(vacationId);
        }

        vacation.setStatus(VacationStatusEnum.CANCELED.getValue());

        Vacation canceledVacation = vacationRepository.save(vacation);
        return vacationMapper.toResponseDTO(canceledVacation);
    }

    private int calculateWorkingDays(LocalDate start, LocalDate end) {
        int workDays = 0;
        LocalDate date = start;
        while (!date.isAfter(end)) {
            if (!(date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                workDays++;
            }
            date = date.plusDays(1);
        }
        return workDays;
    }

    /**
     * Calculates an end date given a start date and number of business days,
     * excluding weekends (Saturday and Sunday).
     *
     * @param startDate    Start date of the vacation
     * @param businessDays Number of business days to calculate
     * @return Calculated end date
     */
    /*private LocalDate calculateEndDateSkippingWeekends(LocalDate startDate, int businessDays) {
        LocalDate result = startDate;
        int addedDays = 0;

        while (addedDays < businessDays) {
            result = result.plusDays(1);
            DayOfWeek day = result.getDayOfWeek();
            if (day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY) {
                addedDays++;
            }
        }

        return result;
    }*/
}
