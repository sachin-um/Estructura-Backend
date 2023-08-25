package com.Estructura.API.repository;

import com.Estructura.API.model.Carpenter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarpenterRepository extends JpaRepository<Carpenter, Integer> {
  Optional<Carpenter> findByEmail(String email);
}
