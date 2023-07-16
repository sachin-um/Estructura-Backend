package com.Estructura.API.repository;

import com.Estructura.API.model.Professional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessionalRepository extends JpaRepository<Professional,Integer> {
}
