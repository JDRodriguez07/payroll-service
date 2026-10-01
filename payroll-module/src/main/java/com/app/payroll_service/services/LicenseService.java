package com.app.payroll_service.services;

import java.time.LocalDate;
import java.time.DayOfWeek;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.app.payroll_service.dto.LicenseResponseDTO;
import com.app.payroll_service.dto.RequestLicenseDTO;
import com.app.payroll_service.enums.LicenseStatusEnum;
import com.app.payroll_service.exceptions.*;
import com.app.payroll_service.mapper.LicenseMapper;
import com.app.payroll_service.models.License;
import com.app.payroll_service.models.LicenseType;
import com.app.payroll_service.repository.LicenseRepository;
import com.app.payroll_service.repository.LicenseTypeRepository;

/**
 * Service class responsible for managing license requests, approvals,
 * cancellations, and automated status updates.
 */
@Service
public class LicenseService {

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private LicenseTypeRepository licenseTypeRepository;

    @Autowired
    private LicenseMapper licenseMapper;

    /**
     * Handles the creation of a new license request.
     *
     * @param dto the request license DTO
     * @return the created license as a response DTO
     */
    public LicenseResponseDTO requestLicense(RequestLicenseDTO dto) {
        LicenseType licenseType = licenseTypeRepository.findById(dto.getLicenseTypeId())
                .orElseThrow(() -> new LicenseTypeNotFoundException(dto.getLicenseTypeId()));

        if (dto.getEndDate().isBefore(dto.getStartDate())) {
            throw new InvalidLicenseDatesException();
        }

        int days = calculateWorkingDays(dto.getStartDate(), dto.getEndDate());

        License license = licenseMapper.toEntity(dto);
        license.setUserId(dto.getUserId()); // Assumes user ID is valid (handled by external microservice)
        license.setLicenseType(licenseType);
        license.setDays(days);
        license.setStatus(LicenseStatusEnum.PENDING.getValue());

        License savedLicense = licenseRepository.save(license);
        return licenseMapper.toResponseDTO(savedLicense);
    }

    /**
     * Approves a pending license request.
     *
     * @param licenseId the ID of the license to approve
     * @return the updated license as a response DTO
     */
    public LicenseResponseDTO approveLicense(Long licenseId) {
        License license = licenseRepository.findById(licenseId)
                .orElseThrow(() -> new LicenseNotFoundException(licenseId));

        if (!LicenseStatusEnum.PENDING.getValue().equals(license.getStatus())) {
            throw new LicenseStatusNotPendingException(licenseId);
        }

        license.setStatus(LicenseStatusEnum.APPROVED.getValue());
        License approved = licenseRepository.save(license);
        return licenseMapper.toResponseDTO(approved);
    }

    /**
     * Rejects a pending license request.
     *
     * @param licenseId the ID of the license to reject
     * @return the updated license as a response DTO
     */
    public LicenseResponseDTO rejectLicense(Long licenseId) {
        License license = licenseRepository.findById(licenseId)
                .orElseThrow(() -> new LicenseNotFoundException(licenseId));

        if (!LicenseStatusEnum.PENDING.getValue().equals(license.getStatus())) {
            throw new LicenseStatusNotPendingException(licenseId);
        }

        license.setStatus(LicenseStatusEnum.REJECTED.getValue());
        License rejectedLicense = licenseRepository.save(license);
        return licenseMapper.toResponseDTO(rejectedLicense);
    }

    /**
     * Cancels a license request that is either pending or approved.
     *
     * @param licenseId the ID of the license to cancel
     * @return the updated license as a response DTO
     */
    public LicenseResponseDTO cancelLicense(Long licenseId) {
        License license = licenseRepository.findById(licenseId)
                .orElseThrow(() -> new LicenseNotFoundException(licenseId));

        if (!(LicenseStatusEnum.APPROVED.getValue().equals(license.getStatus())
                || LicenseStatusEnum.PENDING.getValue().equals(license.getStatus()))) {
            throw new LicenseCanceledTerminatedRejectedException(licenseId);
        }

        license.setStatus(LicenseStatusEnum.CANCELED.getValue());
        License canceledLicense = licenseRepository.save(license);
        return licenseMapper.toResponseDTO(canceledLicense);
    }

    /**
     * Scheduled method that automatically terminates licenses whose end date has
     * passed.
     * Runs daily at 11:59 PM.
     */
    @Scheduled(cron = "59 23 * * *")
    public void autoTerminateLicense() {
        LocalDate today = LocalDate.now();

        List<License> licensesToTerminate = licenseRepository.findByStatusAndEndDateBeforeOrEndDateEquals(
                LicenseStatusEnum.APPROVED.getValue(),
                today, today);

        for (License license : licensesToTerminate) {
            license.setStatus(LicenseStatusEnum.TERMINATED.getValue());
        }

        licenseRepository.saveAll(licensesToTerminate);
    }

    /**
     * Calculates the number of working days (excluding weekends) between two dates.
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
     * Retrieves all licenses in the system.
     *
     * @return a list of LicenseResponseDTOs
     */
    public List<LicenseResponseDTO> getAllLicenses() {
        List<License> licenses = licenseRepository.findAll();
        return licenseMapper.toResponseDTOList(licenses);
    }

    /**
     * Retrieves a specific license by ID.
     *
     * @param licenseId the ID of the license
     * @return the corresponding LicenseResponseDTO
     */
    public LicenseResponseDTO getLicenseById(Long licenseId) {
        License license = licenseRepository.findById(licenseId)
                .orElseThrow(() -> new LicenseNotFoundException(licenseId));

        return licenseMapper.toResponseDTO(license);
    }

}
