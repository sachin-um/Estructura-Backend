package com.Estructura.API.service;

import com.Estructura.API.model.RetailStore;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface RetailStoreService {
    RetailStore saveRetailStore(RetailStore retailStore);

    ResponseEntity<List<RetailStore>> getAllRetailStore();

    Optional<RetailStore> findById(Integer id);
}
