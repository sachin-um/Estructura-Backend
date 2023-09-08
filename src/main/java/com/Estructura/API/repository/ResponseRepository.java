package com.Estructura.API.repository;

import com.Estructura.API.model.CustomerRequest;
import com.Estructura.API.model.Response;
import com.Estructura.API.model.ServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResponseRepository extends JpaRepository<Response,Long> {
    List<Response> findByCustomerRequest(CustomerRequest customerRequest);
    List<Response> findByServiceProvider(ServiceProvider serviceProvider);
}
