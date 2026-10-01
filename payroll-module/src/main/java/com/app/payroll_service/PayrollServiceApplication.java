package com.app.payroll_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main class for the Payroll Service application.
 * 
 * This class bootstraps the Spring Boot application and enables scheduling
 * for background tasks such as automatic contract and license termination.
 */
@SpringBootApplication
@EnableScheduling
public class PayrollServiceApplication {

    /**
     * Application entry point.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(PayrollServiceApplication.class, args);
    }
}
