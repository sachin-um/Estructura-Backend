package com.Estructura.API.repository;

import com.Estructura.API.model.ServiceArea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceAreaRepository extends
    JpaRepository<ServiceArea, Integer> {
}
