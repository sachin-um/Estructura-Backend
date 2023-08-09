package com.Estructura.API.service;

import com.Estructura.API.model.ServiceProvider;
import com.Estructura.API.repository.ServiceProviderRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ServiceProviderServiceImpl implements ServiceProviderService{
    private final ServiceProviderRepository serviceProviderRepository;
    @Override
    public Optional<ServiceProvider> findById(Integer id) {
        return serviceProviderRepository.findById(id);
    }

    @Override
    public ResponseEntity<List<ServiceProvider>> getAllServiceProviders() {
        List<ServiceProvider> serviceProviders= serviceProviderRepository.findAll();
        if (!serviceProviders.isEmpty()){
            return ResponseEntity.ok(serviceProviders);
        }
        else {
            return ResponseEntity.noContent().build();
        }
    }
}
