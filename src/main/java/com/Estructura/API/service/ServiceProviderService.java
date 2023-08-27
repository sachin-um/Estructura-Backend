package com.Estructura.API.service;

import com.Estructura.API.model.ServiceProvider;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ServiceProviderService {
    Optional<ServiceProvider> findById(Integer id);

    ResponseEntity<List<ServiceProvider>> getAllServiceProviders();
}
