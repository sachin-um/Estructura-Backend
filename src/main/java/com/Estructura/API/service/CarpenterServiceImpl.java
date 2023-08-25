package com.Estructura.API.service;

import com.Estructura.API.exception.UserAlreadyExistsException;
import com.Estructura.API.model.Carpenter;
import com.Estructura.API.repository.CarpenterRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CarpenterServiceImpl implements CarpenterService {
    private final CarpenterRepository carpenterRepository;

    @Override
    public Carpenter saveCarpenter(Carpenter carpenter) {
        Optional<Carpenter> theCarpenter = carpenterRepository.findByEmail(
            carpenter.getEmail());
        if (theCarpenter.isPresent()) {
            throw new UserAlreadyExistsException(
                "A user with" + carpenter.getEmail() + "already exists");
        }
        return carpenterRepository.save(carpenter);
    }
}
