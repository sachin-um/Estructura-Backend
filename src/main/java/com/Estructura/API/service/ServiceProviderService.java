package com.Estructura.API.service;

import com.Estructura.API.model.ServiceProvider;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ServiceProviderService {
    public Optional<ServiceProvider> findById(Integer id);

    public ResponseEntity<List<ServiceProvider>> getAllServiceProviders();
}
