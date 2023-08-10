package com.Estructura.API.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Estructura.API.model.Carpenter;

public interface CarpenterRepository extends JpaRepository<Carpenter, Integer> {
    public Optional<Carpenter> findByEmail(String email);
}
