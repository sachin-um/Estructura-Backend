package com.Estructura.API.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.Estructura.API.exception.UserAlreadyExistsException;
import com.Estructura.API.model.Customer;
import com.Estructura.API.repository.CustomerRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService{
    private final CustomerRepository customerRepository;
    @Override
    public Customer saveCustomer(Customer customer) {
        Optional<Customer> theCustomer=customerRepository.findByEmail(customer.getEmail());
        if (theCustomer.isPresent()){
            throw new UserAlreadyExistsException("A user with" +customer.getEmail() +"already exists");
        }
        return customerRepository.save(customer);
    }
}
