package com.Estructura.API.repository;

import com.Estructura.API.model.Customer;
import com.Estructura.API.model.CustomerPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerPlanRepository extends JpaRepository<CustomerPlan,Long> {
    List<CustomerPlan> findCustomerPlansByCustomer(Customer customer);
}
