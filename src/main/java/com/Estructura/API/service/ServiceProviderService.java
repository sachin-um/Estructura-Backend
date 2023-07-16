package com.Estructura.API.service;

import com.Estructura.API.model.ServiceProvider;

import java.util.Optional;

public interface ServiceProviderService {
    public Optional<ServiceProvider> findById(Integer id);
}
