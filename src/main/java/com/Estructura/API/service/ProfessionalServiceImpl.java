package com.Estructura.API.service;

import com.Estructura.API.model.Professional;
import com.Estructura.API.repository.ProfessionalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProfessionalServiceImpl implements ProfessionalService{
    private final ProfessionalRepository professionalRepository;
    @Override
    public Optional<Professional> findById(Integer id) {
        return professionalRepository.findById(id);
    }
}
