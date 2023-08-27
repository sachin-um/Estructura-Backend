package com.Estructura.API.repository;

import com.Estructura.API.model.Painter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PainterRepository extends JpaRepository<Painter, Integer> {
    Optional<Painter> findByEmail(String email);
}
