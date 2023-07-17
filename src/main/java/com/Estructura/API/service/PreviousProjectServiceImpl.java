package com.Estructura.API.service;

import com.Estructura.API.model.PreviousProject;
import com.Estructura.API.model.Professional;
import com.Estructura.API.repository.PreviousProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PreviousProjectServiceImpl implements PreviousProjectService{
    private  final PreviousProjectRepository previousProjectRepository;
    @Override
    public PreviousProject savePreviousProject(PreviousProject previousProject) {
        return previousProjectRepository.save(previousProject);
    }

    @Override
    public Optional<PreviousProject> getPreviousProjectById(Integer id) {
        return previousProjectRepository.findById(id);
    }

    @Override
    public List<PreviousProject> getPreviousProjectByProfessional(Professional professional) {
        return previousProjectRepository.findAllByProfessional(professional);
    }

    @Override
    public void editPreviousProject(PreviousProject previousProject) {
        previousProjectRepository.save(previousProject);
    }

    @Override
    public void deletePreviousProject(PreviousProject previousProject) {
        previousProjectRepository.delete(previousProject);
    }
}
