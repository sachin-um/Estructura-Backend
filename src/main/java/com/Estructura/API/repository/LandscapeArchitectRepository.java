package com.Estructura.API.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Estructura.API.model.LandscapeArchitect;

public interface LandscapeArchitectRepository extends JpaRepository<LandscapeArchitect, Integer> {
    Optional<LandscapeArchitect> findByEmail(String email);
}
