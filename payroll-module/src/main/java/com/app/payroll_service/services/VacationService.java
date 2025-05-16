package com.app.payroll_service.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.app.payroll_service.dto.RequestVacationDTO;
import com.app.payroll_service.dto.VacationResponseDTO;
import com.app.payroll_service.enums.VacationStatusEnum;
import com.app.payroll_service.exceptions.*;
import com.app.payroll_service.mapper.VacationMapper;
import com.app.payroll_service.models.Vacation;
import com.app.payroll_service.repository.VacationRepository;

/**
 * Service class responsible for handling vacation requests,
 * approvals, rejections, and cancellations.
 */
@Service
public class VacationService {

    @Autowired
    private VacationRepository vacationRepository;

    @Autowired
    private VacationMapper vacationMapper;

    public static final int VACATION_DAYS = 15;

    /**
     * Retrieves all vacation records from the database.
     *
     * @return a list of VacationResponseDTOs
     */
    public List<VacationResponseDTO> getAllVacations() {
        List<Vacation> vacations = vacationRepository.findAll();
        return vacationMapper.toResponseDTOList(vacations);
    }

    /**
     * Retrieves a vacation record by its ID.
     *
     * @param id the vacation ID
     * @return the corresponding VacationResponseDTO
     */
    public VacationResponseDTO getVacationById(Long id) {
        Vacation vacation = vacationRepository.findById(id)
                .orElseThrow(() -> new VacationNotFoundException(id));
        return vacationMapper.toResponseDTO(vacation);
    }

    /**
     * Retrieves all vacations currently in PENDING status.
     *
     * @return a list of VacationResponseDTOs with status PENDING
     */
    public List<VacationResponseDTO> getAllPendingVacations() {
        List<Vacation> pendingVacations = vacationRepository
                .findByStatusIgnoreCase(VacationStatusEnum.PENDING.getValue());
        return vacationMapper.toResponseDTOList(pendingVacations);
    }

    /**
     * Retrieves all vacations currently in ACTIVE status.
     *
     * @return a list of VacationResponseDTOs with status ACTIVE
     */
    public List<VacationResponseDTO> getAllActiveVacations() {
        List<Vacation> activeVacations = vacationRepository
                .findByStatusIgnoreCase(VacationStatusEnum.ACTIVE.getValue());
        return vacationMapper.toResponseDTOList(activeVacations);
    }

    /**
     * Retrieves all vacations currently in APPROVED status.
     *
     * @return a list of VacationResponseDTOs with status APPROVED
     */
    public List<VacationResponseDTO> getAllApprovedVacations() {
        List<Vacation> approvedVacations = vacationRepository
                .findByStatusIgnoreCase(VacationStatusEnum.APPROVED.getValue());
        return vacationMapper.toResponseDTOList(approvedVacations);
    }

    /**
     * Retrieves all vacations currently in TERMINATED status.
     * These vacations have completed their duration.
     *
     * @return a list of VacationResponseDTOs with status TERMINATED
     */
    public List<VacationResponseDTO> getAllTerminatedVacations() {
        List<Vacation> terminatedVacations = vacationRepository
                .findByStatusIgnoreCase(VacationStatusEnum.TERMINATED.getValue());
        return vacationMapper.toResponseDTOList(terminatedVacations);
    }

    /**
     * Retrieves all vacations currently in REJECTED status.
     * These vacations were not approved.
     *
     * @return a list of VacationResponseDTOs with status REJECTED
     */
    public List<VacationResponseDTO> getAllRejectedVacations() {
        List<Vacation> rejectedVacations = vacationRepository
                .findByStatusIgnoreCase(VacationStatusEnum.REJECTED.getValue());
        return vacationMapper.toResponseDTOList(rejectedVacations);
    }

    /**
     * Retrieves all vacations currently in CANCELED status.
     * These vacations were manually canceled before or after approval.
     *
     * @return a list of VacationResponseDTOs with status CANCELED
     */
    public List<VacationResponseDTO> getAllCanceledVacations() {
        List<Vacation> canceledVacations = vacationRepository
                .findByStatusIgnoreCase(VacationStatusEnum.CANCELED.getValue());
        return vacationMapper.toResponseDTOList(canceledVacations);
    }

    /**
     * Processes a new vacation request, validating the date range
     * and required number of business days.
     *
     * @param dto the request DTO
     * @return the created vacation as a response DTO
     */
    public VacationResponseDTO requestVacation(RequestVacationDTO dto) {
        if (dto.getEndDate().isBefore(dto.getStartDate())) {
            throw new InvalidVacationDatesException();
        }

        int days = calculateWorkingDays(dto.getStartDate(), dto.getEndDate());

        if (days != VACATION_DAYS) {
            throw new InvalidVacationDaysException();
        }

        Vacation vacation = vacationMapper.toEntity(dto);
        vacation.setUserId(dto.getUserId()); // Assumes user ID is valid
        vacation.setTakenDays(days);
        vacation.setStatus(VacationStatusEnum.PENDING.getValue());

        Vacation savedVacation = vacationRepository.save(vacation);
        return vacationMapper.toResponseDTO(savedVacation);
    }

    /**
     * Approves a vacation request if it is in pending status.
     *
     * @param vacationId the ID of the vacation request
     * @return the approved vacation as a response DTO
     */
    public VacationResponseDTO approveVacation(Long vacationId) {
        Vacation vacation = vacationRepository.findById(vacationId)
                .orElseThrow(() -> new VacationNotFoundException(vacationId));

        if (!VacationStatusEnum.PENDING.getValue().equals(vacation.getStatus())) {
            throw new VacationStatusNotPendingException(vacationId);
        }

        vacation.setStatus(VacationStatusEnum.APPROVED.getValue());

        Vacation savedVacation = vacationRepository.save(vacation);
        return vacationMapper.toResponseDTO(savedVacation);
    }

    /**
     * Rejects a vacation request if it is in pending status.
     *
     * @param vacationId the ID of the vacation request
     * @return the rejected vacation as a response DTO
     */
    public VacationResponseDTO rejectVacation(Long vacationId) {
        Vacation vacation = vacationRepository.findById(vacationId)
                .orElseThrow(() -> new VacationNotFoundException(vacationId));

        if (!(VacationStatusEnum.PENDING.getValue().equals(vacation.getStatus()) ||
                VacationStatusEnum.APPROVED.getValue().equals(vacation.getStatus()))) {
            throw new VacationStatusNotPendingApprovedException(vacationId);
        }

        vacation.setStatus(VacationStatusEnum.REJECTED.getValue());

        Vacation savedVacation = vacationRepository.save(vacation);
        return vacationMapper.toResponseDTO(savedVacation);
    }

    /**
     * Cancels a vacation request if it is in pending or approved status.
     *
     * @param vacationId the ID of the vacation request
     * @return the canceled vacation as a response DTO
     */
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

    /**
     * Scheduled task that automatically activates vacations.
     * <p>
     * This method runs daily at 1:00 AM and activates all vacation records
     * that are in APPROVED status and have a start date less than or equal to
     * today.
     * It skips execution on weekends.
     * </p>
     * 
     * Business rule: A vacation becomes ACTIVE on its start date to ensure
     * that the day is counted from the beginning of the workday.
     */
    @Scheduled(cron = "0 00 01 * * *") // Every day at 1:00 AM
    public void autoActivateVacations() {
        LocalDate today = LocalDate.now();

        if (!isBusinessDay(today)) {
            return;
        }

        // Activate vacations that were approved and should have already started
        List<Vacation> vacationsToActivate = vacationRepository
                .findByStatusAndStartDateLessThanEqual(
                        VacationStatusEnum.APPROVED.getValue(), today);

        for (Vacation vacation : vacationsToActivate) {
            vacation.setStatus(VacationStatusEnum.ACTIVE.getValue());
        }

        vacationRepository.saveAll(vacationsToActivate);
    }

    /**
     * Scheduled task that automatically terminates approved vacations whose end
     * date
     * is today or earlier. This task runs every day at 11:55 PM but is skipped on
     * weekends.
     * 
     * Business rule: vacation termination should only occur on business days,
     * therefore this method does not run on Saturdays or Sundays.
     * 
     * It updates the status of qualifying vacation records to {@code TERMINATED}.
     */
    @Scheduled(cron = "0 55 23 * * *")
    public void autoTerminateVacations() {
        LocalDate today = LocalDate.now();

        if (!isBusinessDay(today)) {
            return;
        }

        // Fetch vacations in APPROVED status that have ended
        List<Vacation> vacationsToTerminate = vacationRepository
                .findByStatusAndEndDateLessThanEqual(
                        VacationStatusEnum.ACTIVE.getValue(), today);

        // Update their status to TERMINATED
        for (Vacation vacation : vacationsToTerminate) {
            vacation.setStatus(VacationStatusEnum.TERMINATED.getValue());
        }

        vacationRepository.saveAll(vacationsToTerminate);
    }

    /**
     * Calculates the number of working days (Monday–Friday) between two dates.
     *
     * @param start start date
     * @param end   end date
     * @return number of working days
     */
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
     * Checks whether a given date falls on a business day (Monday to Friday).
     *
     * @param date the date to evaluate
     * @return true if the date is a business day; false if it falls on Saturday or
     *         Sunday
     */
    private boolean isBusinessDay(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY;
    }

}
