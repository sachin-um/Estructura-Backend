package com.Estructura.API.service;

import com.Estructura.API.model.PreviousProject;
import com.Estructura.API.model.Professional;
import com.Estructura.API.repository.PreviousProjectRepository;
import com.Estructura.API.requests.projects.ProjectRequest;
import com.Estructura.API.responses.projects.ProjectResponse;
import com.Estructura.API.utils.FileUploadUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PreviousProjectServiceImpl implements PreviousProjectService{
    private  final PreviousProjectRepository previousProjectRepository;
    private final ProfessionalService professionalService;


    @Override
    public ProjectResponse saveOrUpdateProject(ProjectRequest projectRequest) throws IOException {
        ProjectResponse response=new ProjectResponse();
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
            PreviousProject project=previousProjectRepository.save(previousProject);
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
                response.setSuccess(true);
                response.setProject(previousProject);
                return response;
            }

            else {
                response.setErrormessage("Somthing went wrong");
                return response;
            }
        }
        else {
            response.setErrormessage("Invalid professional ID");
            return response;
        }
    }

    @Override
    public ProjectResponse getPreviousProjectById(Integer id) {
        ProjectResponse response=new ProjectResponse();
        Optional<PreviousProject> previousProject= previousProjectRepository.findById(id);
        if (previousProject.isPresent()){
            response.setSuccess(true);
            response.setProject(previousProject.get());
            return response;
        }
        else {
            response.setErrormessage("Cannot find the project");
            return response;
        }
    }

    @Override
    public ProjectResponse getPreviousProjectByProfessional(Professional professional) {
        ProjectResponse response=new ProjectResponse();
        List<PreviousProject> previousProjects= previousProjectRepository.findAllByProfessional(professional);
        if (!previousProjects.isEmpty()){
            response.setSuccess(true);
            response.setProjects(previousProjects);
            return  response;
        }
        else {
            response.setErrormessage("No Projects");
            return response;
        }
    }



    @Override
    public ProjectResponse deletePreviousProject(PreviousProject previousProject) {
        ProjectResponse response=new ProjectResponse();
        previousProjectRepository.delete(previousProject);
        Optional<PreviousProject> project= previousProjectRepository.findById(previousProject.getId());
        if (project.isPresent()){
            response.setErrormessage("Cannot find the project");
            return response;

        }
        else {
            response.setSuccess(true);
            return response;
        }

    }
}
