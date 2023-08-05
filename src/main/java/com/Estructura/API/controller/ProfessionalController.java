package com.Estructura.API.controller;

import com.Estructura.API.model.Professional;
import com.Estructura.API.model.Role;
import com.Estructura.API.service.ProfessionalService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/professionals")
public class ProfessionalController {
    private final ProfessionalService professionalService;

    @GetMapping("/all")
    public ResponseEntity<List<Professional>> getAllProfessionals(){
        return professionalService.getAllProfessionals();
    }

    @GetMapping("/all/{role}")
    public ResponseEntity<List<Professional>> getAllProfessionalsByRole(@PathVariable("role") Role role){
        return professionalService.findByRole(role);
    }
}
