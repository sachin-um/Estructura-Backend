package com.Estructura.API.service;

import com.Estructura.API.model.RetailStore;
import org.springframework.http.ResponseEntity;

import java.util.List;

import java.util.Optional;

public interface RetailStoreService {
    public RetailStore saveRetailStore(RetailStore retailStore);

    public ResponseEntity<List<RetailStore>> getAllRetailStore();

    public Optional<RetailStore> findById(Integer id);
}
