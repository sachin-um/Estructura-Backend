package com.Estructura.API.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Estructura.API.model.MasonWorker;

public interface MasonWorkerRepository extends JpaRepository<MasonWorker, Integer> {
    public Optional<MasonWorker> findByEmail(String email);
}
