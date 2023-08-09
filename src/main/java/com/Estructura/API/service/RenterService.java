package com.Estructura.API.service;

import com.Estructura.API.model.Renter;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface RenterService {
    public Renter saveRenter(Renter renter);
    public Optional<Renter> findById(Integer id);

    public ResponseEntity<List<Renter>> findAll();
}
