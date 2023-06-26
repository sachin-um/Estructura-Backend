package com.Estructura.API.repository;

import com.Estructura.API.model.ServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceProviderRepository extends JpaRepository<ServiceProvider,Integer> {
}
