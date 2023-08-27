package com.Estructura.API.controller;

import com.Estructura.API.model.ServiceProvider;
import com.Estructura.API.service.ServiceProviderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/serviceProviders")
public class ServiceProviderController {
    private final ServiceProviderService serviceProviderService;

    @GetMapping("/all")
    public ResponseEntity<List<ServiceProvider>> getAllServiceProviders() {
        return serviceProviderService.getAllServiceProviders();
    }
}
