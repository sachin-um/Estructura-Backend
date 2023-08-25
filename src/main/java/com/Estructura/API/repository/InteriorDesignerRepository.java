package com.Estructura.API.repository;

import com.Estructura.API.model.InteriorDesigner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InteriorDesignerRepository extends
    JpaRepository<InteriorDesigner, Integer> {
    Optional<InteriorDesigner> findByEmail(String email);
}
