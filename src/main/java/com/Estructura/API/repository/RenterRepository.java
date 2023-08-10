package com.Estructura.API.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Estructura.API.model.Renter;

public interface RenterRepository extends JpaRepository<Renter, Integer> {
    Optional<Renter> findByEmail(String email);
}
