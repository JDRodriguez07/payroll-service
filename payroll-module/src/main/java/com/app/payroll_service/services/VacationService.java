package com.app.payroll_service.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.payroll_service.exceptions.InvalidVacationDatesException;
import com.app.payroll_service.exceptions.InvalidVacationDaysException;
import com.app.payroll_service.exceptions.VacationNotFoundException;
import com.app.payroll_service.models.Vacation;
import com.app.payroll_service.repository.VacationRepository;

@Service
public class VacationService {

    private static final String STATUS_PENDING = "PENDIENTE";
    private static final String STATUS_APPROVED = "APROBADA";
    private static final String STATUS_REJECTED = "RECHAZADA";

    private static final int DEFAULT_VACATION_DAYS = 15;

    @Autowired
    private VacationRepository vacationRepository;

    /**
     * Retrieves all vacations from the database.
     *
     * @return List of vacations
     */
    public List<Vacation> getAllVacations() {
        return vacationRepository.findAll();
    }

    /**
     * Retrieves a specific vacation by ID.
     *
     * @param id Vacation ID
     * @return Vacation
     * @throws VacationNotFoundException if not found
     */
    public Vacation getVacationById(Long id) {
        return vacationRepository.findById(id)
                .orElseThrow(() -> new VacationNotFoundException(id));
    }

    /**
     * Creates a vacation manually, validating dates and taken days.
     *
     * @param vacation Vacation to create
     * @return Created vacation
     */
    public Vacation createVacation(Vacation vacation) {
        if (vacation.getStartDate().isAfter(vacation.getEndDate())) {
            throw new InvalidVacationDatesException();
        }

        if (vacation.getTakenDays() <= 0) {
            throw new InvalidVacationDaysException();
        }

        return vacationRepository.save(vacation);
    }

    /**
     * Allows a user to request vacation using only a start date.
     * The system assigns 15 business days automatically and sets status to
     * "PENDING".
     *
     * @param vacation Vacation request (only userId and startDate required)
     * @return Created vacation
     */
    public Vacation requestVacation(Vacation vacation) {
        if (vacation.getUserId() == null) {
            throw new IllegalArgumentException("User ID is required.");
        }

        if (vacation.getStartDate() == null) {
            throw new InvalidVacationDatesException("Start date is required.");
        }

        LocalDate endDate = calculateEndDateSkippingWeekends(vacation.getStartDate(), DEFAULT_VACATION_DAYS);

        vacation.setEndDate(endDate);
        vacation.setTakenDays(DEFAULT_VACATION_DAYS);
        vacation.setStatus(STATUS_PENDING);

        return vacationRepository.save(vacation);
    }

    /**
     * Calculates an end date given a start date and number of business days,
     * excluding weekends (Saturday and Sunday).
     *
     * @param startDate    Start date of the vacation
     * @param businessDays Number of business days to calculate
     * @return Calculated end date
     */
    private LocalDate calculateEndDateSkippingWeekends(LocalDate startDate, int businessDays) {
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
    }

    /**
     * Approves a vacation by setting its status to "APROBADA".
     *
     * @param id Vacation ID
     * @return Updated vacation
     */
    public Vacation approveVacation(Long id) {
        Vacation vacation = vacationRepository.findById(id)
                .orElseThrow(() -> new VacationNotFoundException(id));

        if (!STATUS_PENDING.equalsIgnoreCase(vacation.getStatus())) {
            throw new IllegalStateException("Only vacations with status 'PENDIENTE' can be approved.");
        }

        vacation.setStatus(STATUS_APPROVED);
        return vacationRepository.save(vacation);
    }

    /**
     * Rejects a vacation by setting its status to "RECHAZADA".
     *
     * @param id Vacation ID
     * @return Updated vacation
     */
    public Vacation rejectVacation(Long id) {
        Vacation vacation = vacationRepository.findById(id)
                .orElseThrow(() -> new VacationNotFoundException(id));

        if (!STATUS_PENDING.equalsIgnoreCase(vacation.getStatus())) {
            throw new IllegalStateException("Only vacations with status 'PENDIENTE' can be rejected.");
        }

        vacation.setStatus(STATUS_REJECTED);
        return vacationRepository.save(vacation);
    }

    /**
     * Updates an existing vacation with new data.
     *
     * @param id      Vacation ID
     * @param updated Updated vacation
     * @return Updated vacation
     */
    public Vacation updateVacation(Long id, Vacation updated) {
        Vacation existing = vacationRepository.findById(id)
                .orElseThrow(() -> new VacationNotFoundException(id));

        if (updated.getStartDate().isAfter(updated.getEndDate())) {
            throw new InvalidVacationDatesException();
        }

        if (updated.getTakenDays() <= 0) {
            throw new InvalidVacationDaysException();
        }

        existing.setUserId(updated.getUserId());
        existing.setStartDate(updated.getStartDate());
        existing.setEndDate(updated.getEndDate());
        existing.setTakenDays(updated.getTakenDays());
        existing.setStatus(updated.getStatus());

        return vacationRepository.save(existing);
    }

    /**
     * Deletes a vacation by ID.
     *
     * @param id Vacation ID
     * @throws VacationNotFoundException if not found
     */
    public void deleteVacation(Long id) {
        if (!vacationRepository.existsById(id)) {
            throw new VacationNotFoundException(id);
        }
        vacationRepository.deleteById(id);
    }

    /**
     * Retrieves all vacation requests with status "PENDIENTE".
     *
     * @return List of pending vacations
     */
    public List<Vacation> getAllPendingVacations() {
        return vacationRepository.findByStatusIgnoreCase(STATUS_PENDING);
    }

}
