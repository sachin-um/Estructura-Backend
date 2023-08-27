package com.Estructura.API.repository;

import com.Estructura.API.model.LandscapeArchitect;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LandscapeArchitectRepository extends
    JpaRepository<LandscapeArchitect, Integer> {
    Optional<LandscapeArchitect> findByEmail(String email);
}
