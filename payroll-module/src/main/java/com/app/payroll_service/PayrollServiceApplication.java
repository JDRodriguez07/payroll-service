package com.app.payroll_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.Bean;


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

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Puedes filtrar aquí si quieres
                        .allowedOrigins("http://localhost:8080" , "https://main.damw0yiexgk9o.amplifyapp.com") 
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*");
            }
        };
    }
}
