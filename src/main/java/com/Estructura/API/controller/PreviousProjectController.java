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
        return saveOrUpdateProject(projectRequest);

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
            return saveOrUpdateProject(projectRequest);
        }
        else {
            return ResponseEntity.notFound().build();
        }

    }

    private ResponseEntity<?> saveOrUpdateProject(@ModelAttribute ProjectRequest projectRequest) throws IOException {
        Optional<Professional> professional=professionalService.findById(projectRequest.getProfessionalId());
        if (professional.isPresent()){
            String mainImageName= StringUtils.cleanPath(projectRequest.getMainImage().getOriginalFilename());
            PreviousProject previousProject=PreviousProject.builder()
                    .name(projectRequest.getName())
                    .description(projectRequest.getDescription())
                    .cost(projectRequest.getCost())
                    .projectFromEstructura(projectRequest.isProjectFromEstructura())
                    .professional(professional.get())
                    .MainImage(mainImageName)
                    .build();
            int count=0;
            for (MultipartFile file:projectRequest.getExtraImages()){
                if (!file.isEmpty()) {
                    String extraImageName=StringUtils.cleanPath(file.getOriginalFilename());
                    if(count==0) previousProject.setExtraImage1(extraImageName);//check the image count is less than 3
                    if(count==1) previousProject.setExtraImage2(extraImageName);
                    if(count==3) previousProject.setExtraImage3(extraImageName);
                    count++;
                }
            }
            count=0;
            for (MultipartFile document:projectRequest.getDocuments()){
                if(!document.isEmpty()){
                    String documentName=StringUtils.cleanPath(document.getOriginalFilename());
                    if(count==0) previousProject.setDocument1(documentName);//check the image count is less than 3
                    if(count==1) previousProject.setDocument2(documentName);
                    if(count==3) previousProject.setDocument3(documentName);
                    count++;
                }
            }
            PreviousProject project=previousProjectService.savePreviousProject(previousProject);
            if (project!=null){
                String uploadDir="./project-files/"+project.getProfessional().getId()+"/"+project.getId();
                FileUploadUtil.saveFile(uploadDir, projectRequest.getMainImage(), mainImageName);
                for (MultipartFile file:projectRequest.getExtraImages()){
                    if (!file.isEmpty()) {
                        String fileName=StringUtils.cleanPath(file.getOriginalFilename());
                        FileUploadUtil.saveFile(uploadDir,file,fileName);
                    }
                }
                for (MultipartFile document:projectRequest.getDocuments()){
                    if (!document.isEmpty()){
                        String fileName=StringUtils.cleanPath(document.getOriginalFilename());
                        FileUploadUtil.saveFile(uploadDir,document,fileName);
                    }
                }

                return ResponseEntity.ok("Success");
            }
            else return ResponseEntity.badRequest().body("Somthing went wrong");
        }
        else return ResponseEntity.badRequest().body("Invalid professional ID");
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
