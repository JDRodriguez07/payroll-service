package com.app.payroll_service.client;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.app.payroll_service.services.HttpService;
import com.app.payroll_service.config.UserManagementApiProperties;

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
                Map.of("Authorization", authorization)
        );

        return response.getBody();
    }

    public Object allEmployeesAndContracts(){
        String url = userManagementApiProperties.getBaseUrl() + "/employee/allEmployeesAndContracts";

        ResponseEntity<Object> response = httpService.get(
                url,
                Object.class,
                Map.of("Authorization", authorization)
        );
    }
}
