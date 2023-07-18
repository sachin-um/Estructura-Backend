package com.Estructura.API.controller;

import com.Estructura.API.model.PreviousProject;
import com.Estructura.API.model.Professional;
import com.Estructura.API.requests.projects.ProjectRequest;
import com.Estructura.API.responses.projects.ProjectResponse;
import com.Estructura.API.service.PreviousProjectService;
import com.Estructura.API.service.ProfessionalService;
import lombok.AllArgsConstructor;
import org.aspectj.apache.bcel.Repository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/projects")
public class PreviousProjectController {
    private final PreviousProjectService previousProjectService;
    private final ProfessionalService professionalService;

    @PostMapping("/add")
    public ResponseEntity<ProjectResponse> addPreviousProject(
            @ModelAttribute ProjectRequest projectRequest) throws IOException {
        ProjectResponse response=previousProjectService.saveOrUpdateProject(projectRequest);
        if(response.isSuccess()){
            return ResponseEntity.ok(response);
        }
        else {
            return ResponseEntity.badRequest().body(response);
        }


    }
    @GetMapping("/project/{projectId}")
    public ResponseEntity<ProjectResponse> previousProject(@PathVariable("projectId") int projectId){
        ProjectResponse response=previousProjectService.getPreviousProjectById(projectId);
        if (response.isSuccess()){
            return ResponseEntity.ok(response);
        }
        else {
            return ResponseEntity.badRequest().body(response);
        }

    }

    @GetMapping("/{userid}")
    public ResponseEntity<ProjectResponse> getPreviousProjects(@PathVariable("userid") int userid){
        Optional<Professional> professional=professionalService.findById(userid);
        ProjectResponse response=new ProjectResponse();
        if (professional.isPresent()){
            response=previousProjectService.getPreviousProjectByProfessional(professional.get());
            return ResponseEntity.ok(response);
        }
        else {
            response.setErrormessage("access denied");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/update/{projectId}")
    public ResponseEntity<ProjectResponse> updatePreviousProject(
            @PathVariable("projectId") int projectId,
            @ModelAttribute ProjectRequest projectRequest) throws IOException {

        ProjectResponse response= previousProjectService.getPreviousProjectById(projectId);
        if (response.isSuccess()){
            response=previousProjectService.saveOrUpdateProject(projectRequest);
            return ResponseEntity.ok(response);
        }
        else {
            return ResponseEntity.badRequest().body(response);
        }

    }

    @DeleteMapping("/delete/{projectId}")
    public ResponseEntity<ProjectResponse> deleteProject(@PathVariable("projectId") int projectId) {
        ProjectResponse response = previousProjectService.getPreviousProjectById(projectId);
        if (response.isSuccess()) {
            ProjectResponse DeleteResponse = previousProjectService.deletePreviousProject(response.getProject());
            if (DeleteResponse.isSuccess()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body(DeleteResponse);
            }
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
}
