package com.Estructura.API.repository;

import com.Estructura.API.model.Electrician;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectricianRepository extends JpaRepository<Electrician, Integer> {
}
