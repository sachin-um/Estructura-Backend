package com.Estructura.API.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Estructura.API.model.Painter;

public interface PainterRepository extends JpaRepository<Painter, Integer> {
    public Optional<Painter> findByEmail(String email);
}
