package com.Estructura.API.repository;

import com.Estructura.API.model.Customer;
import com.Estructura.API.model.RetailItem;
import com.Estructura.API.model.RetailStore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RetailStoreRepository extends JpaRepository<RetailStore,Integer> {
    Optional<RetailStore> findByEmail(String email);
}
