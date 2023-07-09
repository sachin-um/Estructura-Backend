package com.Estructura.API.repository;

import com.Estructura.API.model.Painter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PainterRepository extends JpaRepository<Painter,Integer> {
}
