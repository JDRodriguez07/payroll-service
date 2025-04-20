package com.app.payroll_service.services;

import java.time.LocalDate;
import java.time.DayOfWeek;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.payroll_service.exceptions.InvalidLicenseDatesException;
import com.app.payroll_service.exceptions.InvalidLicenseDaysException;
import com.app.payroll_service.exceptions.LicenseNotFoundException;
import com.app.payroll_service.models.License;
import com.app.payroll_service.repository.LicenseRepository;

@Service
public class LicenseService {

    // Constants representing license status
    private static final String STATUS_PENDING = "PENDIENTE";
    private static final String STATUS_APPROVED = "APROBADA";
    private static final String STATUS_REJECTED = "RECHAZADA";

    @Autowired
    private LicenseRepository licenseRepository;

    /**
     * Retrieves all licenses in the system.
     */
    public List<License> getAllLicenses() {
        return licenseRepository.findAll();
    }

    /**
     * Retrieves a license by its ID.
     *
     * @param id License ID
     * @return License entity
     * @throws LicenseNotFoundException if not found
     */
    public License getLicenseById(Long id) {
        return licenseRepository.findById(id)
                .orElseThrow(() -> new LicenseNotFoundException(id));
    }

    /**
     * Creates a license based on a start date and a specific number of business days.
     *
     * @param license License data
     * @return Saved License
     */
    public License createLicenseWithDays(License license) {
        if (license.getDays() <= 0) {
            throw new InvalidLicenseDaysException();
        }

        if (license.getStartDate() == null) {
            throw new InvalidLicenseDatesException("Start date is required.");
        }

        // Calculate endDate excluding weekends
        LocalDate calculatedEndDate = calculateEndDateSkippingWeekends(license.getStartDate(), license.getDays());
        license.setEndDate(calculatedEndDate);

        return licenseRepository.save(license);
    }

    /**
     * Creates a license based on a start and end date.
     * Automatically calculates the number of business days.
     *
     * @param license License data
     * @return Saved License
     */
    public License createLicenseWithDateRange(License license) {
        if (license.getStartDate() == null || license.getEndDate() == null) {
            throw new InvalidLicenseDatesException("Start and end dates are required.");
        }

        if (license.getStartDate().isAfter(license.getEndDate())) {
            throw new InvalidLicenseDatesException("Start date cannot be after end date.");
        }

        int businessDays = calculateBusinessDays(license.getStartDate(), license.getEndDate());

        if (businessDays <= 0) {
            throw new InvalidLicenseDaysException("The range must contain at least 1 business day.");
        }

        license.setDays(businessDays);
        return licenseRepository.save(license);
    }

    /**
     * Allows a user to request a license using a start date and number of days.
     * Status is set to "PENDING".
     *
     * @param license License request
     * @return Saved License
     */
    public License requestLeaveWithDays(License license) {
        if (license.getUserId() == null) {
            throw new IllegalArgumentException("User ID is required.");
        }

        if (license.getDays() <= 0) {
            throw new InvalidLicenseDaysException();
        }

        if (license.getStartDate() == null) {
            throw new InvalidLicenseDatesException("Start date is required.");
        }

        LocalDate calculatedEndDate = calculateEndDateSkippingWeekends(license.getStartDate(), license.getDays());
        license.setEndDate(calculatedEndDate);
        license.setStatus(STATUS_PENDING);

        return licenseRepository.save(license);
    }

    /**
     * Allows a user to request a license by providing a start and end date.
     * The system calculates the number of business days and sets the status to "PENDING".
     *
     * @param license License request
     * @return Saved License
     */
    public License requestLeaveWithDateRange(License license) {
        if (license.getUserId() == null) {
            throw new IllegalArgumentException("User ID is required.");
        }

        if (license.getStartDate() == null || license.getEndDate() == null) {
            throw new InvalidLicenseDatesException("Start and end dates are required.");
        }

        if (license.getStartDate().isAfter(license.getEndDate())) {
            throw new InvalidLicenseDatesException("Start date cannot be after end date.");
        }

        int businessDays = calculateBusinessDays(license.getStartDate(), license.getEndDate());
        if (businessDays <= 0) {
            throw new InvalidLicenseDaysException("The range must contain at least 1 business day.");
        }

        license.setDays(businessDays);
        license.setStatus(STATUS_PENDING);

        return licenseRepository.save(license);
    }

    /**
     * Approves a pending license by changing its status to "APPROVED".
     *
     * @param licenseId License ID
     * @return Updated License
     */
    public License approveLicense(Long licenseId) {
        License license = licenseRepository.findById(licenseId)
                .orElseThrow(() -> new LicenseNotFoundException(licenseId));

        if (!STATUS_PENDING.equalsIgnoreCase(license.getStatus())) {
            throw new IllegalStateException("Only licenses with status 'PENDIENTE' can be approved.");
        }

        license.setStatus(STATUS_APPROVED);
        return licenseRepository.save(license);
    }

    /**
     * Rejects a pending license by changing its status to "REJECTED".
     *
     * @param licenseId License ID
     * @return Updated License
     */
    public License rejectLicense(Long licenseId) {
        License license = licenseRepository.findById(licenseId)
                .orElseThrow(() -> new LicenseNotFoundException(licenseId));

        if (!STATUS_PENDING.equalsIgnoreCase(license.getStatus())) {
            throw new IllegalStateException("Only licenses with status 'PENDIENTE' can be rejected.");
        }

        license.setStatus(STATUS_REJECTED);
        return licenseRepository.save(license);
    }

    /**
     * Returns all licenses with status "PENDING".
     *
     * @return List of pending licenses
     */
    public List<License> getAllPendingLicenses() {
        return licenseRepository.findByStatusIgnoreCase(STATUS_PENDING);
    }

    /**
     * Deletes a license by ID.
     *
     * @param id License ID
     * @throws LicenseNotFoundException if not found
     */
    public void deleteLicense(Long id) {
        if (!licenseRepository.existsById(id)) {
            throw new LicenseNotFoundException(id);
        }
        licenseRepository.deleteById(id);
    }

    /**
     * Updates an existing license with new data.
     *
     * @param id License ID
     * @param updatedLicense Updated license data
     * @return Updated License
     */
    public License updateLicense(Long id, License updatedLicense) {
        License existingLicense = licenseRepository.findById(id)
                .orElseThrow(() -> new LicenseNotFoundException(id));

        if (updatedLicense.getStartDate() == null) {
            throw new InvalidLicenseDatesException("Start date is required.");
        }

        if (updatedLicense.getDays() <= 0) {
            throw new InvalidLicenseDaysException();
        }

        LocalDate calculatedEndDate = calculateEndDateSkippingWeekends(
                updatedLicense.getStartDate(), updatedLicense.getDays());

        existingLicense.setLicenseType(updatedLicense.getLicenseType());
        existingLicense.setStartDate(updatedLicense.getStartDate());
        existingLicense.setEndDate(calculatedEndDate);
        existingLicense.setDays(updatedLicense.getDays());
        existingLicense.setStatus(updatedLicense.getStatus());

        return licenseRepository.save(existingLicense);
    }

    /**
     * Calculates the end date based on a number of business days (excluding weekends).
     *
     * @param startDate Starting date
     * @param businessDays Number of business days
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
     * Counts the number of business days between two dates (inclusive), excluding weekends.
     *
     * @param startDate Start date
     * @param endDate End date
     * @return Number of business days
     */
    private int calculateBusinessDays(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new InvalidLicenseDatesException("Start date cannot be after end date.");
        }

        int businessDays = 0;
        LocalDate current = startDate;

        while (!current.isAfter(endDate)) {
            DayOfWeek day = current.getDayOfWeek();
            if (day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY) {
                businessDays++;
            }
            current = current.plusDays(1);
        }

        return businessDays;
    }
}
