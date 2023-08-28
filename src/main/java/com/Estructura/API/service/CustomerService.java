package com.Estructura.API.service;

import com.Estructura.API.model.Customer;

import java.util.Optional;

public interface CustomerService {
    Customer saveCustomer(Customer customer);
    Optional<Customer> findById(Integer id);
}
