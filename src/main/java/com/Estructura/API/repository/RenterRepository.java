package com.Estructura.API.repository;

import com.Estructura.API.model.Renter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RenterRepository extends JpaRepository<Renter,Integer> {
    Optional<Renter> findByEmail(String email);
}
