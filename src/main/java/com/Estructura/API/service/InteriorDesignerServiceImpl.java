package com.Estructura.API.service;

import com.Estructura.API.exception.UserAlreadyExistsException;
import com.Estructura.API.model.InteriorDesigner;
import com.Estructura.API.repository.InteriorDesignerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class InteriorDesignerServiceImpl implements InteriorDesignerService{
    private final InteriorDesignerRepository interiorDesignerRepository;
    @Override
    public InteriorDesigner saveInteriorDesigner(InteriorDesigner interiorDesigner) {
        Optional<InteriorDesigner> theInteriorDesigner=interiorDesignerRepository.findByEmail(interiorDesigner.getEmail());
        if (theInteriorDesigner.isPresent()){
            throw new UserAlreadyExistsException("A user with" +interiorDesigner.getEmail() +"already exists");
        }
        return interiorDesignerRepository.save(interiorDesigner);
    }
}
