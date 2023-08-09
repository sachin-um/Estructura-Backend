package com.Estructura.API.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Estructura.API.model.Professional;

public interface ProfessionalRepository extends JpaRepository<Professional, Integer> {
    public Optional<Professional> findByEmail(String email);
}
