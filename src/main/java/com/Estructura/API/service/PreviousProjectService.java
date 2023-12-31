package com.Estructura.API.service;

import com.Estructura.API.model.PreviousProject;
import com.Estructura.API.model.Professional;
import com.Estructura.API.requests.projects.ProjectRequest;
import com.Estructura.API.responses.GenericAddOrUpdateResponse;
import com.Estructura.API.responses.GenericDeleteResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.IOException;
import java.util.List;

public interface PreviousProjectService {
    GenericAddOrUpdateResponse<ProjectRequest> saveOrUpdateProject(
        @ModelAttribute ProjectRequest projectRequest) throws IOException;

    ResponseEntity<PreviousProject> getPreviousProjectById(Integer id);

    GenericAddOrUpdateResponse<ProjectRequest> updatePreviousProject(
        @ModelAttribute ProjectRequest projectRequest,
        Integer id) throws IOException;

    ResponseEntity<List<PreviousProject>> getPreviousProjectByProfessional(
        Professional professional);

    GenericDeleteResponse<Integer> deletePreviousProject(
        PreviousProject previousProject);
}
