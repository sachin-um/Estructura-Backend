package com.Estructura.API.controller;

import com.Estructura.API.model.PreviousProject;
import com.Estructura.API.model.Professional;
import com.Estructura.API.requests.projects.ProjectRequest;
import com.Estructura.API.service.PreviousProjectService;
import com.Estructura.API.service.ProfessionalService;
import com.Estructura.API.utils.FileUploadUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        Optional<Professional> professional=professionalService.findById(projectRequest.getProfessionalId());
        String mainImageName= StringUtils.cleanPath(projectRequest.getMainMultipartFile().getOriginalFilename());

        if (professional.isPresent()){
            PreviousProject previousProject=PreviousProject.builder()
                    .name(projectRequest.getName())
                    .description(projectRequest.getDescription())
                    .cost(projectRequest.getCost())
                    .projectFromEstructura(projectRequest.isProjectFromEstructura())
                    .professional(professional.get())
                    .MainImage(mainImageName)
                    .build();
            int count=0;
            for (MultipartFile file:projectRequest.getExtraMultipartFile()){
                if (!file.isEmpty()) {
                    String extraImageName=StringUtils.cleanPath(file.getOriginalFilename());
                    if(count==0) previousProject.setExtraImage1(extraImageName);
                    if(count==1) previousProject.setExtraImage2(extraImageName);
                    if(count==3) previousProject.setExtraImage3(extraImageName);
                    count++;
                }
            };
            PreviousProject project=previousProjectService.savePreviousProject(previousProject);
            if (project!=null){
                String uploadDir="./project-files/"+project.getId();
                FileUploadUtil.saveFile(uploadDir, projectRequest.getMainMultipartFile(), mainImageName);
                for (MultipartFile file:projectRequest.getExtraMultipartFile()){
                    if (!file.isEmpty()) {
                        String fileName=StringUtils.cleanPath(file.getOriginalFilename());
                        FileUploadUtil.saveFile(uploadDir,file,fileName);
                    }
                }

                return ResponseEntity.ok("Success");
            }
            else return ResponseEntity.badRequest().body("Somthing went wrong");
        }
        else return ResponseEntity.badRequest().body("Invalid professional ID");

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
            String mainImageName= StringUtils.cleanPath(projectRequest.getMainMultipartFile().getOriginalFilename());
                PreviousProject projectt=PreviousProject.builder()
                        .name(projectRequest.getName())
                        .description(projectRequest.getDescription())
                        .cost(projectRequest.getCost())
                        .projectFromEstructura(projectRequest.isProjectFromEstructura())
                        .professional(previousProject.get().getProfessional())
                        .MainImage(mainImageName)
                        .build();
                int count=0;
                for (MultipartFile file:projectRequest.getExtraMultipartFile()){
                    if (!file.isEmpty()) {
                        String extraImageName=StringUtils.cleanPath(file.getOriginalFilename());
                        if(count==0)  projectt.setExtraImage1(extraImageName);
                        if(count==1)  projectt.setExtraImage2(extraImageName);
                        if(count==3)  projectt.setExtraImage3(extraImageName);
                        count++;
                    }
                };
                PreviousProject project=previousProjectService.savePreviousProject(projectt);
                if (project!=null){
                    String uploadDir="./project-files/"+project.getId();
                    FileUploadUtil.saveFile(uploadDir,projectRequest.getMainMultipartFile(),mainImageName);
                    for (MultipartFile file:projectRequest.getExtraMultipartFile()){
                        if (!file.isEmpty()) {
                            String fileName=StringUtils.cleanPath(file.getOriginalFilename());
                            FileUploadUtil.saveFile(uploadDir,file,fileName);
                        }
                    };

                    return ResponseEntity.ok("Success");
                }
                else return ResponseEntity.badRequest().body("Somthing went wrong");

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
