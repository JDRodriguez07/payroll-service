package com.app.payroll_service.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.app.payroll_service.dto.RequestVacationDTO;
import com.app.payroll_service.dto.VacationResponseDTO;
import com.app.payroll_service.services.VacationService;

public class VacationControllerTest {

    @Mock
    private VacationService vacationService;

    @InjectMocks
    private VacationController vacationController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    mockMvc = MockMvcBuilders.standaloneSetup(vacationController).build();
  
    }

    @Test
void testCancelVacation() {
    Long vacationId = 1L;
    VacationResponseDTO responseDTO = new VacationResponseDTO();
    when(vacationService.cancelVacation(vacationId)).thenReturn(responseDTO);

    ResponseEntity<VacationResponseDTO> response = vacationController.cancelVacation(vacationId);

    assertEquals(200, response.getStatusCodeValue());
    assertEquals(responseDTO, response.getBody());
    verify(vacationService, times(1)).cancelVacation(vacationId);
}

    @Test
    void testRejectVacation() {
        Long vacationId = 1L;
        VacationResponseDTO responseDTO = new VacationResponseDTO();
        when(vacationService.rejectVacation(vacationId)).thenReturn(responseDTO);
    
        ResponseEntity<VacationResponseDTO> response = vacationController.rejectVacation(vacationId);
    
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(responseDTO, response.getBody());
        verify(vacationService, times(1)).rejectVacation(vacationId);
    }
    

    @Test
    void testRequestVacation() {
        RequestVacationDTO dto = new RequestVacationDTO();
        
        VacationResponseDTO responseDTO = new VacationResponseDTO();
        
        when(vacationService.requestVacation(dto)).thenReturn(responseDTO);
        
        ResponseEntity<VacationResponseDTO> response = vacationController.requestVacation(dto);
        
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(responseDTO, response.getBody());
        verify(vacationService, times(1)).requestVacation(dto);
    }

    @Test
    void testApproveVacation() {
        Long vacationId = 1L;
        VacationResponseDTO responseDTO = new VacationResponseDTO();
        
        when(vacationService.approveVacation(vacationId)).thenReturn(responseDTO);
        
        ResponseEntity<VacationResponseDTO> response = vacationController.approveVacation(vacationId);
        
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(responseDTO, response.getBody());
        verify(vacationService, times(1)).approveVacation(vacationId);
    }
    
    @Test
    void testGetAllVacations() {
        List<VacationResponseDTO> vacations = Arrays.asList(new VacationResponseDTO(), new VacationResponseDTO());
        
        when(vacationService.getAllVacations()).thenReturn(vacations);
        
        ResponseEntity<List<VacationResponseDTO>> response = vacationController.getAllVacations();
        
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(vacations, response.getBody());
        verify(vacationService, times(1)).getAllVacations();
    }
    
    @Test
    void testGetVacationById() {
        Long vacationId = 1L;
        VacationResponseDTO responseDTO = new VacationResponseDTO();
        
        when(vacationService.getVacationById(vacationId)).thenReturn(responseDTO);
        
        ResponseEntity<VacationResponseDTO> response = vacationController.getVacationById(vacationId);
        
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(responseDTO, response.getBody());
        verify(vacationService, times(1)).getVacationById(vacationId);
    }
    @Test
void getAllPendingVacations_shouldReturnList() throws Exception {
    List<VacationResponseDTO> vacations = Arrays.asList(new VacationResponseDTO(), new VacationResponseDTO());
    when(vacationService.getAllPendingVacations()).thenReturn(vacations);

    mockMvc.perform(get("/vacations/pending"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(2));
}
@Test
void getAllActiveVacations_shouldReturnList() throws Exception {
    List<VacationResponseDTO> vacations = Arrays.asList(new VacationResponseDTO());
    when(vacationService.getAllActiveVacations()).thenReturn(vacations);

    mockMvc.perform(get("/vacations/active"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(1));
}
@Test
void getAllApprovedVacations_shouldReturnList() throws Exception {
    List<VacationResponseDTO> vacations = Arrays.asList(new VacationResponseDTO());
    when(vacationService.getAllApprovedVacations()).thenReturn(vacations);

    mockMvc.perform(get("/vacations/approved"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(1));
}
@Test
void getAllTerminatedVacations_shouldReturnList() throws Exception {
    List<VacationResponseDTO> vacations = Arrays.asList(new VacationResponseDTO());
    when(vacationService.getAllTerminatedVacations()).thenReturn(vacations);

    mockMvc.perform(get("/vacations/terminated"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(1));
}
@Test
void getAllRejectedVacations_shouldReturnList() throws Exception {
    List<VacationResponseDTO> vacations = Arrays.asList(new VacationResponseDTO());
    when(vacationService.getAllRejectedVacations()).thenReturn(vacations);

    mockMvc.perform(get("/vacations/rejected"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(1));
}
@Test
void getAllCanceledVacations_shouldReturnList() throws Exception {
    List<VacationResponseDTO> vacations = Arrays.asList(new VacationResponseDTO());
    when(vacationService.getAllCanceledVacations()).thenReturn(vacations);

    mockMvc.perform(get("/vacations/canceled"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(1));
}

}
