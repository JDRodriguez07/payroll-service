package com.app.payroll_service.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

import com.app.payroll_service.dto.RequestVacationDTO;
import com.app.payroll_service.dto.VacationResponseDTO;
import com.app.payroll_service.enums.VacationStatusEnum;
import com.app.payroll_service.exceptions.InvalidVacationDatesException;
import com.app.payroll_service.exceptions.InvalidVacationDaysException;
import com.app.payroll_service.exceptions.VacationNotFoundException;
import com.app.payroll_service.exceptions.VacationStatusNotPendingApprovedException;
import com.app.payroll_service.exceptions.VacationStatusNotPendingException;
import com.app.payroll_service.mapper.VacationMapper;
import com.app.payroll_service.models.Vacation;
import com.app.payroll_service.repository.VacationRepository;

class VacationServiceTest {

    @InjectMocks
    private VacationService vacationService;

    @Mock
    private VacationRepository vacationRepository;

    @Mock
    private VacationMapper vacationMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Vacation mockVacation(Long id, String status, LocalDate startDate, LocalDate endDate) {
        Vacation v = new Vacation();
        v.setVacationId(id);
        v.setUserId(1L);
        v.setStatus(status);
        v.setStartDate(startDate);
        v.setEndDate(endDate);
        v.setTakenDays(15);
        return v;
    }

    @Test
    void testGetAllVacations() {
        List<Vacation> vacations = List.of(new Vacation());
        List<VacationResponseDTO> dtos = List.of(new VacationResponseDTO());

        when(vacationRepository.findAll()).thenReturn(vacations);
        when(vacationMapper.toResponseDTOList(vacations)).thenReturn(dtos);

        assertEquals(dtos, vacationService.getAllVacations());
    }

    @Test
    void testGetVacationById_Found() {
        Vacation vacation = new Vacation();
        vacation.setVacationId(1L);
        VacationResponseDTO dto = new VacationResponseDTO();

        when(vacationRepository.findById(1L)).thenReturn(Optional.of(vacation));
        when(vacationMapper.toResponseDTO(vacation)).thenReturn(dto);

        assertEquals(dto, vacationService.getVacationById(1L));
    }

    @Test
    void testGetVacationById_NotFound() {
        when(vacationRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(VacationNotFoundException.class, () -> vacationService.getVacationById(1L));
    }

    @Test
    void testGetAllPendingVacations() {
        when(vacationRepository.findByStatusIgnoreCase("PENDIENTE")).thenReturn(List.of(new Vacation()));
        when(vacationMapper.toResponseDTOList(anyList())).thenReturn(List.of(new VacationResponseDTO()));
        assertEquals(1, vacationService.getAllPendingVacations().size());
    }

    @Test
    void testGetAllApprovedVacations() {
        when(vacationRepository.findByStatusIgnoreCase("APROBADA")).thenReturn(List.of(new Vacation()));
        when(vacationMapper.toResponseDTOList(anyList())).thenReturn(List.of(new VacationResponseDTO()));
        assertEquals(1, vacationService.getAllApprovedVacations().size());
    }

    @Test
    void testGetAllActiveVacations() {
        when(vacationRepository.findByStatusIgnoreCase("ACTIVA")).thenReturn(List.of(new Vacation()));
        when(vacationMapper.toResponseDTOList(anyList())).thenReturn(List.of(new VacationResponseDTO()));
        assertEquals(1, vacationService.getAllActiveVacations().size());
    }

    @Test
    void testGetAllTerminatedVacations() {
        when(vacationRepository.findByStatusIgnoreCase("TERMINADA")).thenReturn(List.of(new Vacation()));
        when(vacationMapper.toResponseDTOList(anyList())).thenReturn(List.of(new VacationResponseDTO()));
        assertEquals(1, vacationService.getAllTerminatedVacations().size());
    }

    @Test
    void testGetAllRejectedVacations() {
        when(vacationRepository.findByStatusIgnoreCase("RECHAZADA")).thenReturn(List.of(new Vacation()));
        when(vacationMapper.toResponseDTOList(anyList())).thenReturn(List.of(new VacationResponseDTO()));
        assertEquals(1, vacationService.getAllRejectedVacations().size());
    }

    @Test
    void testGetAllCanceledVacations() {
        when(vacationRepository.findByStatusIgnoreCase("CANCELADA")).thenReturn(List.of(new Vacation()));
        when(vacationMapper.toResponseDTOList(anyList())).thenReturn(List.of(new VacationResponseDTO()));
        assertEquals(1, vacationService.getAllCanceledVacations().size());
    }

    @Test
    void testRequestVacation_Success() {
        RequestVacationDTO dto = new RequestVacationDTO();
        dto.setUserId(1L);
        dto.setStartDate(LocalDate.of(2025, 5, 1));
        dto.setEndDate(LocalDate.of(2025, 5, 21));

        Vacation entity = new Vacation();
        entity.setStatus("PENDIENTE");

        Vacation saved = new Vacation();
        VacationResponseDTO response = new VacationResponseDTO();

        when(vacationMapper.toEntity(dto)).thenReturn(entity);
        when(vacationRepository.save(any())).thenReturn(saved);
        when(vacationMapper.toResponseDTO(saved)).thenReturn(response);

        assertEquals(response, vacationService.requestVacation(dto));
    }

    @Test
    void testRequestVacation_InvalidDates() {
        RequestVacationDTO dto = new RequestVacationDTO();
        dto.setStartDate(LocalDate.of(2025, 5, 10));
        dto.setEndDate(LocalDate.of(2025, 5, 5));
        assertThrows(InvalidVacationDatesException.class, () -> vacationService.requestVacation(dto));
    }

    @Test
    void testRequestVacation_InvalidDays() {
        RequestVacationDTO dto = new RequestVacationDTO();
        dto.setStartDate(LocalDate.of(2025, 5, 1));
        dto.setEndDate(LocalDate.of(2025, 5, 2)); // Solo 2 días hábiles

        assertThrows(InvalidVacationDaysException.class, () -> vacationService.requestVacation(dto));
    }

    @Test
    void testApproveVacation_Success() {
        Vacation v = mockVacation(1L, "PENDIENTE", LocalDate.now(), LocalDate.now().plusDays(15));
        when(vacationRepository.findById(1L)).thenReturn(Optional.of(v));
        when(vacationRepository.save(any())).thenReturn(v);
        when(vacationMapper.toResponseDTO(v)).thenReturn(new VacationResponseDTO());

        assertNotNull(vacationService.approveVacation(1L));
    }

    @Test
    void testApproveVacation_NotPending() {
        Vacation v = mockVacation(1L, "RECHAZADA", LocalDate.now(), LocalDate.now().plusDays(15));
        when(vacationRepository.findById(1L)).thenReturn(Optional.of(v));
        assertThrows(VacationStatusNotPendingException.class, () -> vacationService.approveVacation(1L));
    }

    @Test
    void testRejectVacation_Success() {
        Vacation v = mockVacation(1L, "PENDIENTE", LocalDate.now(), LocalDate.now().plusDays(15));
        when(vacationRepository.findById(1L)).thenReturn(Optional.of(v));
        when(vacationRepository.save(any())).thenReturn(v);
        when(vacationMapper.toResponseDTO(v)).thenReturn(new VacationResponseDTO());

        assertNotNull(vacationService.rejectVacation(1L));
    }

    @Test
    void testRejectVacation_InvalidStatus() {
        Vacation v = mockVacation(1L, "CANCELADA", LocalDate.now(), LocalDate.now().plusDays(15));
        when(vacationRepository.findById(1L)).thenReturn(Optional.of(v));
        assertThrows(VacationStatusNotPendingApprovedException.class, () -> vacationService.rejectVacation(1L));
    }

    @Test
    void testCancelVacation_Success() {
        Vacation v = mockVacation(1L, "APROBADA", LocalDate.now(), LocalDate.now().plusDays(15));
        when(vacationRepository.findById(1L)).thenReturn(Optional.of(v));
        when(vacationRepository.save(any())).thenReturn(v);
        when(vacationMapper.toResponseDTO(v)).thenReturn(new VacationResponseDTO());

        assertNotNull(vacationService.cancelVacation(1L));
    }

    @Test
    void testCancelVacation_InvalidStatus() {
        Vacation v = mockVacation(1L, "TERMINADA", LocalDate.now(), LocalDate.now().plusDays(15));
        when(vacationRepository.findById(1L)).thenReturn(Optional.of(v));
        assertThrows(VacationStatusNotPendingApprovedException.class, () -> vacationService.cancelVacation(1L));
    }

@Test
void shouldNotActivateVacations_whenTodayIsWeekend() {
    // Arrange
    LocalDate saturday = LocalDate.of(2025, 5, 24); // Un sábado
    try (MockedStatic<LocalDate> mockedDate = Mockito.mockStatic(LocalDate.class)) {
        mockedDate.when(LocalDate::now).thenReturn(saturday);

        // Act
        vacationService.autoActivateVacations();

        // Assert
        verify(vacationRepository, never()).findByStatusAndStartDateLessThanEqual(anyString(), any());
        verify(vacationRepository, never()).saveAll(any());
    }
}
@Test
void shouldActivateApprovedVacationsStartingToday_whenTodayIsBusinessDay() {
    LocalDate businessDay = LocalDate.of(2025, 5, 26); // lunes
    Vacation vacation = new Vacation();
    vacation.setStatus(VacationStatusEnum.APPROVED.getValue());
    vacation.setStartDate(businessDay);

    List<Vacation> approvedVacations = List.of(vacation);

    when(vacationRepository.findByStatusAndStartDateLessThanEqual(
            VacationStatusEnum.APPROVED.getValue(), businessDay))
            .thenReturn(approvedVacations);

    try (MockedStatic<LocalDate> mockedDate = Mockito.mockStatic(LocalDate.class)) {
        mockedDate.when(LocalDate::now).thenReturn(businessDay);

        vacationService.autoActivateVacations();

        verify(vacationRepository, times(1)).saveAll(approvedVacations);
        assertEquals(VacationStatusEnum.ACTIVE.getValue(), vacation.getStatus());
    }
}
@Test
void shouldTerminateActiveVacationsThatEndedToday_whenTodayIsBusinessDay() {
    LocalDate businessDay = LocalDate.of(2025, 5, 26); // lunes
    Vacation vacation = new Vacation();
    vacation.setStatus(VacationStatusEnum.ACTIVE.getValue());
    vacation.setEndDate(businessDay);
    vacation.setTakenDays(15);

    List<Vacation> vacationsToTerminate = List.of(vacation);

    when(vacationRepository.findByStatusAndEndDateLessThanEqual(
            VacationStatusEnum.ACTIVE.getValue(), businessDay))
            .thenReturn(vacationsToTerminate);

    try (MockedStatic<LocalDate> mockedDate = Mockito.mockStatic(LocalDate.class)) {
        mockedDate.when(LocalDate::now).thenReturn(businessDay);

        vacationService.autoTerminateVacations();

        verify(vacationRepository, times(1)).saveAll(vacationsToTerminate);
        assertEquals(VacationStatusEnum.TERMINATED.getValue(), vacation.getStatus());
    }
}
}



