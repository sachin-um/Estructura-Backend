package com.Estructura.API.repository;

import com.Estructura.API.model.CustomerRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRequestRepository extends JpaRepository<CustomerRequest,Integer> {
}
