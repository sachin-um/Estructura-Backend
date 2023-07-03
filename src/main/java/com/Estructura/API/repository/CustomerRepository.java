package com.Estructura.API.repository;

import com.Estructura.API.model.Customer;
import com.Estructura.API.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    Optional<User> findByEmail(String email);
}
