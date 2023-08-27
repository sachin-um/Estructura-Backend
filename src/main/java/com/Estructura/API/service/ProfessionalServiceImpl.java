package com.Estructura.API.service;

import com.Estructura.API.model.Professional;
import com.Estructura.API.model.Role;
import com.Estructura.API.repository.ProfessionalRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProfessionalServiceImpl implements ProfessionalService {
    private final ProfessionalRepository professionalRepository;

    @Override
    public Optional<Professional> findById(Integer id) {
        return professionalRepository.findById(id);
    }

    @Override
    public ResponseEntity<List<Professional>> getAllProfessionals() {
        List<Professional> professionals = professionalRepository.findAll();
        if (!professionals.isEmpty()) {
            return ResponseEntity.ok(professionals);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @Override
    public ResponseEntity<List<Professional>> findByRole(Role role) {
        List<Professional> professionals =
            professionalRepository.findProfessionalByRole(
                role);
        if (!professionals.isEmpty()) {
            return ResponseEntity.ok(professionals);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}
