package com.Estructura.API.repository;

import com.Estructura.API.model.ConstructionCompany;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConstructionCompanyRepository extends
    JpaRepository<ConstructionCompany, Integer> {
    Optional<ConstructionCompany> findByEmail(String email);
}
