package com.Estructura.API.service;

import com.Estructura.API.model.Professional;

import java.util.Optional;

public interface ProfessionalService {
    public Optional<Professional> findById(Integer id);
}
