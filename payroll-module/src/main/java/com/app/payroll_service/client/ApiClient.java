package com.app.payroll_service.client;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.app.payroll_service.services.HttpService;
import com.app.payroll_service.config.UserManagementApiProperties;
import com.app.payroll_service.dto.EmployeeAndContract;

@Component
public class ApiClient {

    @Autowired
    private HttpService httpService;

    @Autowired
    private UserManagementApiProperties userManagementApiProperties;

    public boolean employeeExists(Long id, String authorization) {
        String url = userManagementApiProperties.getBaseUrl() + "/employee/exists/" + id;

        ResponseEntity<Boolean> response = httpService.get(
                url,
                Boolean.class,
                Map.of("Authorization", authorization));

        return response.getBody();
    }

    public List<EmployeeAndContract> allEmployeesAndContracts(String authorization) {
        String url = userManagementApiProperties.getBaseUrl() + "/employee/allEmployeesAndContracts";

        ResponseEntity<EmployeeAndContract[]> response = httpService.get(
                url,
                EmployeeAndContract[].class,
                Map.of("Authorization", authorization));

        return List.of(response.getBody());
    }
}
