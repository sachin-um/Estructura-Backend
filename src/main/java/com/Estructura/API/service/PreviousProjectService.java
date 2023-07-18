package com.Estructura.API.service;

import com.Estructura.API.model.PreviousProject;
import com.Estructura.API.model.Professional;
import com.Estructura.API.requests.projects.ProjectRequest;
import com.Estructura.API.responses.projects.ProjectResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface PreviousProjectService {
    public ProjectResponse saveOrUpdateProject(@ModelAttribute ProjectRequest projectRequest) throws IOException;
    public ProjectResponse getPreviousProjectById(Integer id);
    public ProjectResponse getPreviousProjectByProfessional(Professional professional);
    public ProjectResponse deletePreviousProject(PreviousProject previousProject);
}
