package com.Estructura.API.service;

import com.Estructura.API.model.Renter;

import java.util.Optional;

public interface RenterService {
    public Renter saveRenter(Renter renter);
    public Optional<Renter> findById(Integer id);
}
