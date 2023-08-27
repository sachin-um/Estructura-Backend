package com.Estructura.API.service;

import com.Estructura.API.model.Renter;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface RenterService {
    Renter saveRenter(Renter renter);

    Optional<Renter> findById(Integer id);

    ResponseEntity<List<Renter>> findAll();
}
