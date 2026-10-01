package com.app.payroll_service.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.payroll_service.exceptions.VacationNotFoundException;
import com.app.payroll_service.models.Vacation;
import com.app.payroll_service.repository.VacationRepository;

@Service
public class VacationService {

    @Autowired
    private VacationRepository vacationRepository;

    public List<Vacation> getAllVacations() {
        return vacationRepository.findAll();
    }

    public Vacation getVacationById(Long id) {
        return vacationRepository.findById(id)
                .orElseThrow(() -> new VacationNotFoundException(id));
    }

    public Vacation createVacation(Vacation vacation) {
        return vacationRepository.save(vacation);
    }

    public void deleteVacation(Long id) {
        if (!vacationRepository.existsById(id)) {
            throw new VacationNotFoundException(id);
        }
        vacationRepository.deleteById(id);
    }

    public Vacation updateVacation(Long id, Vacation updated) {
        Vacation existing = vacationRepository.findById(id)
                .orElseThrow(() -> new VacationNotFoundException(id));

        existing.setUserId(updated.getUserId());
        existing.setStartDate(updated.getStartDate());
        existing.setEndDate(updated.getEndDate());
        existing.setTakenDays(updated.getTakenDays());
        existing.setStatus(updated.getStatus());

        return vacationRepository.save(existing);
    }
}
