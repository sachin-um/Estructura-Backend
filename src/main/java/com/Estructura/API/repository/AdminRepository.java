package com.Estructura.API.repository;

import com.Estructura.API.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin,Integer> {
    Optional<Admin> findByEmail(String email);
}
