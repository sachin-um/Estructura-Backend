package com.Estructura.API.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Estructura.API.model.ConstructionCompany;

public interface ConstructionCompanyRepository extends JpaRepository<ConstructionCompany, Integer> {
    public Optional<ConstructionCompany> findByEmail(String email);
}
