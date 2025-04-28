package com.app.payroll_service.services;

import java.time.LocalDate;
import java.time.DayOfWeek;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.payroll_service.dto.LicenseResponseDTO;
import com.app.payroll_service.dto.RequestLicenseDTO;
import com.app.payroll_service.enums.LicenseStatusEnum;
import com.app.payroll_service.exceptions.InvalidLicenseDatesException;
import com.app.payroll_service.exceptions.InvalidLicenseDaysException;
import com.app.payroll_service.exceptions.LicenseStatusNotPendingException;
import com.app.payroll_service.exceptions.LicenseNotFoundException;
import com.app.payroll_service.exceptions.LicenseTypeNotFoundException;
import com.app.payroll_service.mapper.LicenseMapper;
import com.app.payroll_service.models.License;
import com.app.payroll_service.models.LicenseType;
import com.app.payroll_service.repository.LicenseRepository;
import com.app.payroll_service.repository.LicenseTypeRepository;

@Service
public class LicenseService {

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private LicenseTypeRepository licenseTypeRepository;

    @Autowired
    private LicenseMapper licenseMapper;

    public LicenseResponseDTO requestLicense(RequestLicenseDTO dto) {
        // Buscar el tipo de licencia
        LicenseType licenseType = licenseTypeRepository.findById(dto.getLicenseTypeId())
                .orElseThrow(() -> new LicenseTypeNotFoundException(dto.getLicenseTypeId()));

        // Validar fechas
        if (dto.getEndDate().isBefore(dto.getStartDate())) {
            throw new InvalidLicenseDatesException();
        }

        // Calcular días hábiles (sin contar sábados ni domingos)
        int days = calculateWorkingDays(dto.getStartDate(), dto.getEndDate());

        // Mapear DTO a entidad
        License license = licenseMapper.toEntity(dto);
        license.setLicenseType(licenseType);
        license.setDays(days);
        license.setStatus(LicenseStatusEnum.PENDING.getValue());

        // Guardar
        License savedLicense = licenseRepository.save(license);

        // Retornar respuesta
        return licenseMapper.toResponseDTO(savedLicense);
    }

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

}
