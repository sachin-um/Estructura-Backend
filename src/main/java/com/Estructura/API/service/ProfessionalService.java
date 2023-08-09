package com.Estructura.API.service;

import com.Estructura.API.model.Professional;
import com.Estructura.API.model.Role;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ProfessionalService {
    public Optional<Professional> findById(Integer id);

    public ResponseEntity<List<Professional>> getAllProfessionals();

    public ResponseEntity<List<Professional>> findByRole(Role role);
}
