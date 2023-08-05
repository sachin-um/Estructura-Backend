package com.Estructura.API.service;

import com.Estructura.API.model.RetailStore;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RetailStoreService {
    public RetailStore saveRetailStore(RetailStore retailStore);

    public ResponseEntity<List<RetailStore>> getAllRetailStore();
}
