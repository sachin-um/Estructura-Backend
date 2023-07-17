package com.Estructura.API.service;

import com.Estructura.API.model.PreviousProject;
import com.Estructura.API.model.Professional;
import com.Estructura.API.requests.projects.ProjectRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface PreviousProjectService {
    public ResponseEntity<?> saveOrUpdateProject(@ModelAttribute ProjectRequest projectRequest) throws IOException;
    public Optional<PreviousProject> getPreviousProjectById(Integer id);
    public List<PreviousProject> getPreviousProjectByProfessional(Professional professional);
    public void editPreviousProject(PreviousProject previousProject);
    public void deletePreviousProject(PreviousProject previousProject);
}
