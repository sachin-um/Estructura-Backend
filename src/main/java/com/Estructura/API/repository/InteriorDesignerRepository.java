package com.Estructura.API.repository;

import com.Estructura.API.model.InteriorDesigner;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InteriorDesignerRepository extends JpaRepository<InteriorDesigner,Integer> {
    public Optional<InteriorDesigner> findByEmail(String email);
}
