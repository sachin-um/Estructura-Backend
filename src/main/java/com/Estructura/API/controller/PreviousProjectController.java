package com.Estructura.API.controller;

import com.Estructura.API.model.PreviousProject;
import com.Estructura.API.model.Professional;
import com.Estructura.API.requests.projects.ProjectRequest;
import com.Estructura.API.service.PreviousProjectService;
import com.Estructura.API.service.ProfessionalService;
import lombok.AllArgsConstructor;
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
    public ResponseEntity<?> addPreviousProject(
            @ModelAttribute ProjectRequest projectRequest) throws IOException {
        return previousProjectService.saveOrUpdateProject(projectRequest);

    }
    @GetMapping("/project/{projectId}")
    public Optional<PreviousProject> previousProject(@PathVariable("projectId") int projectId){
        return previousProjectService.getPreviousProjectById(projectId);
    }

    @GetMapping("/{userid}")
    public List<PreviousProject> getPreviousProjects(@PathVariable("userid") int userid){
        Optional<Professional> professional=professionalService.findById(userid);
        if (professional.isPresent()){

            return previousProjectService.getPreviousProjectByProfessional(professional.get());
        }
        else return Collections.emptyList();
    }

    @PostMapping("/update/{projectId}")
    public ResponseEntity<?> updatePreviousProject(
            @PathVariable("projectId") int projectId,
            @ModelAttribute ProjectRequest projectRequest) throws IOException {
        Optional<PreviousProject> previousProject= previousProjectService.getPreviousProjectById(projectId);
        if (previousProject.isPresent()){
            return previousProjectService.saveOrUpdateProject(projectRequest);
        }
        else {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/delete/{projectId}")
    public void deleteProject(@PathVariable("projectId") int projectId){
        Optional<PreviousProject> previousProject=previousProjectService.getPreviousProjectById(projectId);
        if (previousProject.isPresent()){
            previousProjectService.deletePreviousProject(previousProject.get());
        }
        else {
            System.out.println("something went wrong");
            //error;
        }

    }
}
