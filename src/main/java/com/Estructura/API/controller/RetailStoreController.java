package com.Estructura.API.controller;

import com.Estructura.API.model.RetailStore;
import com.Estructura.API.service.RetailStoreService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/retailStore")
public class RetailStoreController {
    private final RetailStoreService retailStoreService;

    @GetMapping("/all")
    public ResponseEntity<List<RetailStore>> getAllRetailStores() {
        return retailStoreService.getAllRetailStore();
    }
}
