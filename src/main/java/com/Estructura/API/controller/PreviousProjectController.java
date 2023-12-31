package com.Estructura.API.controller;

import com.Estructura.API.model.PreviousProject;
import com.Estructura.API.model.Professional;
import com.Estructura.API.requests.projects.ProjectRequest;
import com.Estructura.API.responses.GenericAddOrUpdateResponse;
import com.Estructura.API.responses.GenericDeleteResponse;
import com.Estructura.API.service.PreviousProjectService;
import com.Estructura.API.service.ProfessionalService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/projects")
public class PreviousProjectController {
    private final PreviousProjectService previousProjectService;
    private final ProfessionalService professionalService;

    @PostMapping("/add")
    public GenericAddOrUpdateResponse<ProjectRequest> addPreviousProject(
        @ModelAttribute ProjectRequest projectRequest) throws IOException {
        return previousProjectService.saveOrUpdateProject(projectRequest);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<PreviousProject> previousProject(
        @PathVariable("projectId") int projectId) {
        return previousProjectService.getPreviousProjectById(projectId);
    }

    @GetMapping("/all/{userid}")
    public ResponseEntity<List<PreviousProject>> getPreviousProjects(
        @PathVariable("userid") int userid) {
        Optional<Professional> professional = professionalService.findById(
            userid);
        if (professional.isPresent()) {
            return previousProjectService.getPreviousProjectByProfessional(
                professional.get());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    //
    @PostMapping("/update/{projectId}")
    public GenericAddOrUpdateResponse<ProjectRequest> updatePreviousProject(
        @PathVariable("projectId") int projectId,
        @ModelAttribute ProjectRequest projectRequest) throws IOException {
        return previousProjectService.updatePreviousProject(projectRequest,
                                                            projectId
        );
    }

    @DeleteMapping("/delete/{projectId}")
    public GenericDeleteResponse<Integer> deleteProject(
        @PathVariable("projectId") int projectId) {
        GenericDeleteResponse<Integer> response = new GenericDeleteResponse<>();
        ResponseEntity<PreviousProject> project =
            previousProjectService.getPreviousProjectById(
            projectId);
        if (project.getStatusCode().is2xxSuccessful()) {
            return previousProjectService.deletePreviousProject(
                project.getBody());

        } else {
            response.setSuccess(false);
            return response;
        }
    }
}
