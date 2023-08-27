package com.Estructura.API.repository;

import com.Estructura.API.model.MasonWorker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MasonWorkerRepository extends
    JpaRepository<MasonWorker, Integer> {
    Optional<MasonWorker> findByEmail(String email);
}
