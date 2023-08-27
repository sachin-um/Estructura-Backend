package com.Estructura.API.controller;

import com.Estructura.API.model.Renter;
import com.Estructura.API.service.RenterService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/rentingStore")
public class RentingStoreController {
    private final RenterService renterService;

    @GetMapping("/all")
    public ResponseEntity<List<Renter>> getAllRentingStores() {
        return renterService.findAll();
    }
}
