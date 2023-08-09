package com.Estructura.API.repository;

import com.Estructura.API.model.Professional;
import com.Estructura.API.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfessionalRepository extends JpaRepository<Professional,Integer> {
    List<Professional> findProfessionalByRole(Role role);
}
