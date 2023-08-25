package com.Estructura.API.service;

import com.Estructura.API.exception.UserAlreadyExistsException;
import com.Estructura.API.model.LandscapeArchitect;
import com.Estructura.API.repository.LandscapeArchitectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LandscapeArchitectServiceImpl implements
    LandscapeArchitectService {
    private final LandscapeArchitectRepository landscapeArchitectRepository;

    @Override
    public LandscapeArchitect saveLandscapeArchitect(
        LandscapeArchitect landscapeArchitect) {
        Optional<LandscapeArchitect> theArchitect = landscapeArchitectRepository
            .findByEmail(landscapeArchitect.getEmail());
        if (theArchitect.isPresent()) {
            throw new UserAlreadyExistsException(
                "A user with" + landscapeArchitect.getEmail() +
                "already exists");
        }
        return landscapeArchitectRepository.save(landscapeArchitect);
    }
}
