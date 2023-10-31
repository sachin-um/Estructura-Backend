package com.Estructura.API.repository;

import com.Estructura.API.model.Customer;
import com.Estructura.API.model.CustomerRequest;
import com.Estructura.API.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRequestRepository extends JpaRepository<CustomerRequest,Long> {
    List<CustomerRequest> findByTargetCategoriesRole(Role role);
    Optional<CustomerRequest> findCustomerRequestById(Long id);

    List<CustomerRequest> findCustomerRequestByCustomer(Customer customer);
}
