package com.Estructura.API.service;

import com.Estructura.API.model.RetailStore;

import java.util.Optional;

public interface RetailStoreService {
    public RetailStore saveRetailStore(RetailStore retailStore);

    public Optional<RetailStore> findById(Integer id);
}
