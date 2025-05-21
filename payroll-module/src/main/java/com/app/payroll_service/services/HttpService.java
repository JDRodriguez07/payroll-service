package com.app.payroll_service.services;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class HttpService {

    private final RestTemplate restTemplate;

    public HttpService() {
        this.restTemplate = new RestTemplate();
    }
    // Método GET
    public <T> ResponseEntity<T> get(String url, Class<T> responseType, Map<String, String> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach(httpHeaders::set);
        HttpEntity<Void> entity = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(url, HttpMethod.GET, entity, responseType);
    }

    // Método POST
    public <T, R> ResponseEntity<T> post(String url, R requestBody, Class<T> responseType, Map<String, String> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach(httpHeaders::set);
        HttpEntity<R> entity = new HttpEntity<>(requestBody, httpHeaders);
        return restTemplate.exchange(url, HttpMethod.POST, entity, responseType);
    }

    // Método PUT
    public <T, R> ResponseEntity<T> put(String url, R requestBody, Class<T> responseType, Map<String, String> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach(httpHeaders::set);
        HttpEntity<R> entity = new HttpEntity<>(requestBody, httpHeaders);
        return restTemplate.exchange(url, HttpMethod.PUT, entity, responseType);
    }

    // Método DELETE
    public <T> ResponseEntity<T> delete(String url, Class<T> responseType, Map<String, String> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach(httpHeaders::set);
        HttpEntity<Void> entity = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(url, HttpMethod.DELETE, entity, responseType);
    }
}
