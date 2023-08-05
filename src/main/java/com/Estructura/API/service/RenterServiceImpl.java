package com.Estructura.API.service;

import com.Estructura.API.exception.UserAlreadyExistsException;
import com.Estructura.API.model.Renter;
import com.Estructura.API.repository.RenterRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RenterServiceImpl implements RenterService{
    private final RenterRepository renterRepository;
    @Override
    public Renter saveRenter(Renter renter) {
        Optional<Renter> theRetailStore=renterRepository.findByEmail(renter.getEmail());
        if (theRetailStore.isPresent()){
            throw new UserAlreadyExistsException("A user with" +renter.getEmail() +"already exists");
        }
        return renterRepository.save(renter);
    }

    @Override
    public Optional<Renter> findById(Integer id) {
        return renterRepository.findById(id);
    }

    @Override
    public ResponseEntity<List<Renter>> findAll() {
        List<Renter> renters= renterRepository.findAll();
        if (!renters.isEmpty()){
            return ResponseEntity.ok(renters);
        }
        else {
            return ResponseEntity.noContent().build();
        }
    }

}
