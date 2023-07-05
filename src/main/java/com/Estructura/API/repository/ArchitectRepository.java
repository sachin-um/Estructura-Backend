package com.Estructura.API.repository;

import com.Estructura.API.model.Admin;
import com.Estructura.API.model.Architect;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArchitectRepository extends JpaRepository<Architect,Integer> {
    Optional<Architect> findByEmail(String email);
}
