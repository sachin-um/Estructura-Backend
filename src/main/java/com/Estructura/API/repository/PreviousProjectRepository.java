package com.Estructura.API.repository;

import com.Estructura.API.model.PreviousProject;
import com.Estructura.API.model.Professional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PreviousProjectRepository extends
    JpaRepository<PreviousProject, Integer> {
    List<PreviousProject> findAllByProfessional(Professional professional);

}
