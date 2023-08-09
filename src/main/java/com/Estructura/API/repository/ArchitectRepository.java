package com.Estructura.API.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Estructura.API.model.Architect;

public interface ArchitectRepository extends JpaRepository<Architect,Integer> {
    Optional<Architect> findByEmail(String email);
}
