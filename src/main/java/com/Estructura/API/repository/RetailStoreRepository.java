package com.Estructura.API.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Estructura.API.model.RetailStore;

public interface RetailStoreRepository extends JpaRepository<RetailStore, Integer> {
    Optional<RetailStore> findByEmail(String email);
}
