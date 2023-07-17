package com.Estructura.API.service;

import com.Estructura.API.model.ServiceProvider;
import com.Estructura.API.repository.ServiceProviderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ServiceProviderServiceImpl implements ServiceProviderService{
    private final ServiceProviderRepository serviceProviderRepository;
    @Override
    public Optional<ServiceProvider> findById(Integer id) {
        return serviceProviderRepository.findById(id);
    }
}
