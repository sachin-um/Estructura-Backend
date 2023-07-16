package com.Estructura.API.service;

import com.Estructura.API.model.PreviousProject;
import com.Estructura.API.model.Professional;

import java.util.List;
import java.util.Optional;

public interface PreviousProjectService {
    public PreviousProject savePreviousProject(PreviousProject previousProject);
    public Optional<PreviousProject> getPreviousProjectById(Integer id);
    public List<PreviousProject> getPreviousProjectByProfessional(Professional professional);
    public void editPreviousProject(PreviousProject previousProject);
    public void deletePreviousProject(PreviousProject previousProject);
}
