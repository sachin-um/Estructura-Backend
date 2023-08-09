package com.Estructura.API.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.Estructura.API.exception.UserAlreadyExistsException;
import com.Estructura.API.model.Renter;
import com.Estructura.API.repository.RenterRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RenterServiceImpl implements RenterService {
    private final RenterRepository renterRepository;

    @Override
    public Renter saveRenter(Renter renter) {
        Optional<Renter> theRetailStore = renterRepository.findByEmail(renter.getEmail());
        if (theRetailStore.isPresent()) {
            throw new UserAlreadyExistsException("A user with" + renter.getEmail() + "already exists");
        }
        return renterRepository.save(renter);
    }

    @Override
    public Optional<Renter> findById(Integer id) {
        return renterRepository.findById(id);
    }

}
