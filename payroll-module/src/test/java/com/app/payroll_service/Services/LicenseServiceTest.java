package com.app.payroll_service.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.app.payroll_service.dto.LicenseResponseDTO;
import com.app.payroll_service.dto.RequestLicenseDTO;
import com.app.payroll_service.dto.UpdateContractDTO;
import com.app.payroll_service.enums.LicenseStatusEnum;
import com.app.payroll_service.exceptions.InvalidTerminationDateException;
import com.app.payroll_service.exceptions.LicenseCanceledTerminatedRejectedException;
import com.app.payroll_service.exceptions.LicenseNotPendingApprovedStatusException;
import com.app.payroll_service.exceptions.TerminationDateNotAllowedException;
import com.app.payroll_service.mapper.LicenseMapper;
import com.app.payroll_service.models.Contract;
import com.app.payroll_service.models.ContractType;
import com.app.payroll_service.models.License;
import com.app.payroll_service.models.LicenseType;
import com.app.payroll_service.repository.ContractRepository;
import com.app.payroll_service.repository.LicenseRepository;
import com.app.payroll_service.repository.LicenseTypeRepository;
import com.app.payroll_service.services.ContractService;
import com.app.payroll_service.services.LicenseService;

@DataJpaTest
public class LicenseServiceTest {

    @InjectMocks
    private LicenseService licenseService;

    @InjectMocks
    private ContractService contractService;

    @Mock
    private LicenseRepository licenseRepository;

    @Mock
    private LicenseTypeRepository licenseTypeRepository;

    @Mock
    private LicenseMapper licenseMapper;

    @Mock
    private ContractRepository contractRepository;

    
    

    private RequestLicenseDTO requestDTO;
    private License license;
    private LicenseType licenseType;
    private LicenseResponseDTO responseDTO;

    

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        licenseType = new LicenseType();
        licenseType.setLicenseTypeId(1L);

        requestDTO = new RequestLicenseDTO();
        requestDTO.setLicenseTypeId(1L);
        requestDTO.setUserId(99L);
        requestDTO.setStartDate(LocalDate.of(2025, 5, 20));
        requestDTO.setEndDate(LocalDate.of(2025, 5, 22));

        license = new License();
        license.setLicenseId(1L);
        license.setStatus(LicenseStatusEnum.PENDING.getValue());
        license.setStartDate(requestDTO.getStartDate());
        license.setEndDate(requestDTO.getEndDate());
        license.setUserId(99L);
        license.setLicenseType(licenseType);
        license.setDays(3);

        responseDTO = new LicenseResponseDTO();
        responseDTO.setLicenseId(1L);
    }

    @Test
    public void testRequestLicense_success() {
        when(licenseTypeRepository.findById(1L)).thenReturn(Optional.of(licenseType));
        when(licenseMapper.toEntity(requestDTO)).thenReturn(license);
        when(licenseRepository.save(any())).thenReturn(license);
        when(licenseMapper.toResponseDTO(any())).thenReturn(responseDTO);

        LicenseResponseDTO result = licenseService.requestLicense(requestDTO);
        assertNotNull(result);
        assertEquals(1L, result.getLicenseId());
    }

    @Test
    public void testApproveLicense_success() {
        license.setStatus(LicenseStatusEnum.PENDING.getValue());

        when(licenseRepository.findById(1L)).thenReturn(Optional.of(license));
        when(licenseRepository.save(any())).thenReturn(license);
        when(licenseMapper.toResponseDTO(any())).thenReturn(responseDTO);

        LicenseResponseDTO result = licenseService.approveLicense(1L);
        assertNotNull(result);
    }

    @Test
    public void testRejectLicense_success() {
        license.setStatus(LicenseStatusEnum.APPROVED.getValue());

        when(licenseRepository.findById(1L)).thenReturn(Optional.of(license));
        when(licenseRepository.save(any())).thenReturn(license);
        when(licenseMapper.toResponseDTO(any())).thenReturn(responseDTO);

        LicenseResponseDTO result = licenseService.rejectLicense(1L);
        assertNotNull(result);
    }

    @Test
    public void testCancelLicense_success() {
        license.setStatus(LicenseStatusEnum.APPROVED.getValue());

        when(licenseRepository.findById(1L)).thenReturn(Optional.of(license));
        when(licenseRepository.save(any())).thenReturn(license);
        when(licenseMapper.toResponseDTO(any())).thenReturn(responseDTO);

        LicenseResponseDTO result = licenseService.cancelLicense(1L);
        assertNotNull(result);
    }

   
    @Test
    void shouldTerminateActiveLicensesThatEndedToday_whenTodayIsWeekday() {
        LocalDate today = LocalDate.of(2025, 5, 23); // Viernes
        License license = new License();
        license.setStatus(LicenseStatusEnum.ACTIVE.getValue());
        license.setEndDate(today);
    
        List<License> licenses = List.of(license);
    
        try (MockedStatic<LocalDate> mockedDate = Mockito.mockStatic(LocalDate.class)) {
            mockedDate.when(LocalDate::now).thenReturn(today);
    
            when(licenseRepository.findByStatusAndEndDateLessThanEqual(
                    LicenseStatusEnum.ACTIVE.getValue(), today)).thenReturn(licenses);
    
            licenseService.autoTerminateLicense(); // Asegúrate de que el método esté en licenseService
    
            assertEquals(LicenseStatusEnum.TERMINATED.getValue(), license.getStatus());
            verify(licenseRepository, times(1)).saveAll(licenses);
        }
    }
    @Test
    void shouldActivateApprovedLicensesThatStartedToday_whenTodayIsWeekday() {
        LocalDate today = LocalDate.of(2025, 5, 23); // Viernes
    
        License license = new License();
        license.setStatus(LicenseStatusEnum.APPROVED.getValue());
        license.setStartDate(today);
    
        List<License> licenses = List.of(license);
    
        try (MockedStatic<LocalDate> mockedDate = Mockito.mockStatic(LocalDate.class)) {
            mockedDate.when(LocalDate::now).thenReturn(today);
    
            when(licenseRepository.findByStatusAndStartDateLessThanEqual(
                    LicenseStatusEnum.APPROVED.getValue(), today)).thenReturn(licenses);
    
            licenseService.autoActivateLicense(); // Asegúrate que el método esté en licenseService
    
            assertEquals(LicenseStatusEnum.ACTIVE.getValue(), license.getStatus());
            verify(licenseRepository, times(1)).saveAll(licenses);
        }
    }

    @Test
void rejectLicense_shouldThrowException_whenStatusIsNotPendingOrApproved() {
    Long licenseId = 1L;
    License license = new License();
    license.setLicenseId(licenseId);
    license.setStatus(LicenseStatusEnum.ACTIVE.getValue()); // Estado no permitido para rechazo

    when(licenseRepository.findById(licenseId)).thenReturn(Optional.of(license));

    assertThrows(LicenseNotPendingApprovedStatusException.class, () -> {
        licenseService.rejectLicense(licenseId);
    });

    verify(licenseRepository, never()).save(any());
}
@Test
void updateContract_shouldThrowInvalidTerminationDateException_whenTerminationDateBeforeHireDate() {
    Long contractId = 1L;
    UpdateContractDTO dto = new UpdateContractDTO();
    dto.setTerminationDate(LocalDate.of(2023, 1, 1));  // Fecha de término antes de hireDate

    ContractType contractType = new ContractType();
    contractType.setContractTypeId(2L);
    contractType.setRequiresEndDate(true);

    Contract existingContract = new Contract();
    existingContract.setContractType(contractType);
    existingContract.setHireDate(LocalDate.of(2023, 2, 1)); // hireDate posterior

    when(contractRepository.findById(contractId)).thenReturn(Optional.of(existingContract));

    assertThrows(InvalidTerminationDateException.class, () -> {
        contractService.updateContract(contractId, dto);
    });
}



@Test
void updateContract_shouldThrowTerminationDateNotAllowedException_whenTerminationDateProvidedButNotAllowed() {
    Long contractId = 1L;
    UpdateContractDTO dto = new UpdateContractDTO();
    dto.setTerminationDate(LocalDate.now());

    ContractType contractType = new ContractType();
    contractType.setContractTypeId(1L);
    contractType.setRequiresEndDate(false);

    Contract existingContract = new Contract();
    existingContract.setContractType(contractType);

    when(contractRepository.findById(contractId)).thenReturn(Optional.of(existingContract));

    assertThrows(TerminationDateNotAllowedException.class, () -> {
        contractService.updateContract(contractId, dto);
    });
}

@Test
void cancelLicense_shouldThrowException_whenStatusIsNotPendingOrApproved() {
    Long licenseId = 1L;
    License license = new License();
    license.setLicenseId(licenseId);
    license.setStatus(LicenseStatusEnum.TERMINATED.getValue()); // Estado no permitido para cancelar

    when(licenseRepository.findById(licenseId)).thenReturn(Optional.of(license));

    assertThrows(LicenseCanceledTerminatedRejectedException.class, () -> {
        licenseService.cancelLicense(licenseId);
    });

    verify(licenseRepository, never()).save(any());
}


    @Test
    void shouldNotActivateLicenses_whenTodayIsWeekend() {
        LocalDate sunday = LocalDate.of(2025, 5, 25); // Domingo
    
        try (MockedStatic<LocalDate> mockedDate = Mockito.mockStatic(LocalDate.class)) {
            mockedDate.when(LocalDate::now).thenReturn(sunday);
    
            licenseService.autoActivateLicense();
    
            verifyNoInteractions(licenseRepository);
        }
    }
        
    @Test
void shouldNotTerminateLicenses_whenTodayIsWeekend() {
    LocalDate saturday = LocalDate.of(2025, 5, 24); // Sábado

    try (MockedStatic<LocalDate> mockedDate = Mockito.mockStatic(LocalDate.class)) {
        mockedDate.when(LocalDate::now).thenReturn(saturday);

        licenseService.autoTerminateLicense();

        verifyNoInteractions(licenseRepository);
    }
}
    

    @Test
    public void testGetLicenseById_success() {
        when(licenseRepository.findById(1L)).thenReturn(Optional.of(license));
        when(licenseMapper.toResponseDTO(license)).thenReturn(responseDTO);

        LicenseResponseDTO result = licenseService.getLicenseById(1L);
        assertNotNull(result);
    }

    @Test
    public void testGetAllLicenses() {
        when(licenseRepository.findAll()).thenReturn(List.of(license));
        when(licenseMapper.toResponseDTOList(any())).thenReturn(List.of(responseDTO));

        List<LicenseResponseDTO> result = licenseService.getAllLicenses();
        assertEquals(1, result.size());
    }

    @Test
    public void testGetAllPendingLicenses() {
        when(licenseRepository.findByStatusIgnoreCase(any()))
                .thenReturn(List.of(license));
        when(licenseMapper.toResponseDTOList(any()))
                .thenReturn(List.of(responseDTO));

        List<LicenseResponseDTO> result = licenseService.getAllPendingLicenses();
        assertEquals(1, result.size());
    }

    @Test
    public void testGetAllApprovedLicenses() {
        when(licenseRepository.findByStatusIgnoreCase(any()))
                .thenReturn(List.of(license));
        when(licenseMapper.toResponseDTOList(any()))
                .thenReturn(List.of(responseDTO));

        List<LicenseResponseDTO> result = licenseService.getAllApprovedLicenses();
        assertEquals(1, result.size());
    }

    @Test
    public void testGetAllRejectedLicenses() {
        when(licenseRepository.findByStatusIgnoreCase(any()))
                .thenReturn(List.of(license));
        when(licenseMapper.toResponseDTOList(any()))
                .thenReturn(List.of(responseDTO));

        List<LicenseResponseDTO> result = licenseService.getAllRejectedLicenses();
        assertEquals(1, result.size());
    }

    @Test
    public void testGetAllCanceledLicenses() {
        when(licenseRepository.findByStatusIgnoreCase(any()))
                .thenReturn(List.of(license));
        when(licenseMapper.toResponseDTOList(any()))
                .thenReturn(List.of(responseDTO));

        List<LicenseResponseDTO> result = licenseService.getAllCanceledLicenses();
        assertEquals(1, result.size());
    }

    @Test
    public void testGetAllTerminatedLicenses() {
        when(licenseRepository.findByStatusIgnoreCase(any()))
                .thenReturn(List.of(license));
        when(licenseMapper.toResponseDTOList(any()))
                .thenReturn(List.of(responseDTO));

        List<LicenseResponseDTO> result = licenseService.getAllTerminatedLicenses();
        assertEquals(1, result.size());
    }

    @Test
    public void testGetAllActiveLicenses() {
        when(licenseRepository.findByStatusIgnoreCase(any()))
                .thenReturn(List.of(license));
        when(licenseMapper.toResponseDTOList(any()))
                .thenReturn(List.of(responseDTO));

        List<LicenseResponseDTO> result = licenseService.getAllActiveLicenses();
        assertEquals(1, result.size());
    }

}

