package com.Estructura.API.service;

import com.Estructura.API.exception.UserAlreadyExistsException;
import com.Estructura.API.model.Architect;
import com.Estructura.API.repository.ArchitectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ArchitectServiceImpl implements ArchitectService{
    private final ArchitectRepository architectRepository;
    @Override
    public Architect saveArchitect(Architect architect) {
        Optional<Architect> theArchitect=architectRepository.findByEmail(architect.getEmail());
        if (theArchitect.isPresent()){
            throw new UserAlreadyExistsException("A user with" +architect.getEmail() +"already exists");
        }
        return architectRepository.save(architect);
    }
}
