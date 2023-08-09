package com.Estructura.API.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.Estructura.API.exception.UserAlreadyExistsException;
import com.Estructura.API.model.RetailStore;
import com.Estructura.API.repository.RetailStoreRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RetailStoreServiceImpl implements RetailStoreService {
    private final RetailStoreRepository retailStoreRepository;

    @Override
    public RetailStore saveRetailStore(RetailStore retailStore) {
        Optional<RetailStore> theRetailStore = retailStoreRepository.findByEmail(retailStore.getEmail());
        if (theRetailStore.isPresent()) {
            throw new UserAlreadyExistsException("A user with" + retailStore.getEmail() + "already exists");
        }
        return retailStoreRepository.save(retailStore);
    }

    @Override
    public Optional<RetailStore> findById(Integer id) {
        return retailStoreRepository.findById(id);
    }
}
