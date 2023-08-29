package com.Estructura.API.repository;

import com.Estructura.API.model.CustomerRequest;
import com.Estructura.API.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRequestRepository extends JpaRepository<CustomerRequest,Integer> {
//    List<CustomerRequest> findCustomerRequestByRole(Role role);
}
