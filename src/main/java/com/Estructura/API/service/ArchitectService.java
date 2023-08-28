package com.Estructura.API.service;

import com.Estructura.API.model.Architect;

import java.util.Optional;

public interface ArchitectService {
    Architect saveArchitect(Architect architect);
    Optional<Architect> findArchitectById(Integer id);
}
