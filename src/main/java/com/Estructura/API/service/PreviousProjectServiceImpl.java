package com.Estructura.API.service;

import com.Estructura.API.model.PreviousProject;
import com.Estructura.API.model.Professional;
import com.Estructura.API.repository.PreviousProjectRepository;
import com.Estructura.API.requests.projects.ProjectRequest;
import com.Estructura.API.responses.GenericAddOrUpdateResponse;
import com.Estructura.API.responses.GenericDeleteResponse;
import com.Estructura.API.utils.FileUploadUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public GenericAddOrUpdateResponse<ProjectRequest> saveOrUpdateProject(ProjectRequest projectRequest) throws IOException {
        GenericAddOrUpdateResponse<ProjectRequest> response=new GenericAddOrUpdateResponse<>();
        if (response.checkValidity(projectRequest)){
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
                        .MainImageName(FileUploadUtil.generateFileName(mainImageName))
                        .build();
                if (projectRequest.getLocation()!=null){
                    previousProject.setLocation(projectRequest.getLocation());
                }
                int count=0;
                for (MultipartFile file:projectRequest.getExtraImages()){
                    if (!file.isEmpty()) {
                        String extraImageName=StringUtils.cleanPath(file.getOriginalFilename());
                        if(count==0) previousProject.setExtraImage1(extraImageName); previousProject.setExtraImage1Name(FileUploadUtil.generateFileName(extraImageName));//check the image count is less than 3
                        if(count==1) previousProject.setExtraImage2(extraImageName);previousProject.setExtraImage2Name(FileUploadUtil.generateFileName(extraImageName));
                        if(count==3) previousProject.setExtraImage3(extraImageName);previousProject.setExtraImage3Name(FileUploadUtil.generateFileName(extraImageName));
                        count++;
                    }
                }
                count=0;
                for (MultipartFile document:projectRequest.getDocuments()){
                    if(!document.isEmpty()){
                        String documentName=StringUtils.cleanPath(document.getOriginalFilename());
                        if(count==0) previousProject.setDocument1(documentName); previousProject.setDocument1Name(FileUploadUtil.generateFileName(documentName));//check the image count is less than 3
                        if(count==1) previousProject.setDocument2(documentName); previousProject.setDocument2Name(FileUploadUtil.generateFileName(documentName));
                        if(count==3) previousProject.setDocument3(documentName); previousProject.setDocument2Name(FileUploadUtil.generateFileName(documentName));
                        count++;
                    }
                }
                PreviousProject project=previousProjectRepository.save(previousProject);
                count=0;
                if (project!=null){
                    String uploadDir="./project-files/"+project.getProfessional().getId()+"/"+project.getId();
                    FileUploadUtil.saveFile(uploadDir, projectRequest.getMainImage(), project.getMainImageName());
                    for (MultipartFile file:projectRequest.getExtraImages()){
                        if (!file.isEmpty()) {
                            if(count==0){
                                String fileName=StringUtils.cleanPath(project.getExtraImage1Name());
                                FileUploadUtil.saveFile(uploadDir,file,fileName);
                            }
                            if(count==1){
                                String fileName=StringUtils.cleanPath(project.getExtraImage2Name());
                                FileUploadUtil.saveFile(uploadDir,file,fileName);
                            }
                            if(count==2){
                                String fileName=StringUtils.cleanPath(project.getExtraImage3Name());
                                FileUploadUtil.saveFile(uploadDir,file,fileName);
                            }
                            count++;

                        }
                    }
                    count=0;
                    for (MultipartFile document:projectRequest.getDocuments()){
                        if (!document.isEmpty()){
                            if(count==0){
                                String fileName=StringUtils.cleanPath(project.getDocument1Name());
                                FileUploadUtil.saveFile(uploadDir,document,fileName);
                            }
                            if(count==1){
                                String fileName=StringUtils.cleanPath(project.getDocument2Name());
                                FileUploadUtil.saveFile(uploadDir,document,fileName);
                            }
                            if(count==2){
                                String fileName=StringUtils.cleanPath(project.getDocument3Name());
                                FileUploadUtil.saveFile(uploadDir,document,fileName);
                            }
                            count++;
                            String fileName=StringUtils.cleanPath(document.getOriginalFilename());
                            FileUploadUtil.saveFile(uploadDir,document,fileName);
                        }
                    }
                    response.setSuccess(true);
                    response.setId(project.getId());
                    return response;
                }

                else {
                    response.addError("fatal","Somthing went wrong");
                    return response;
                }
            }
            else {
                response.addError("fatal","Invalid professional ID");
                return response;
            }

        }
        return response;
    }

    @Override
    public ResponseEntity<PreviousProject> getPreviousProjectById(Integer id) {
        Optional<PreviousProject> previousProject= previousProjectRepository.findById(id);
        return previousProject.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
//
    @Override
    public ResponseEntity<List<PreviousProject>> getPreviousProjectByProfessional(Professional professional) {
        List<PreviousProject> previousProjects= previousProjectRepository.findAllByProfessional(professional);
        if (!previousProjects.isEmpty()){
            return  ResponseEntity.ok(previousProjects);
        }
        else {
            return ResponseEntity.noContent().build();
        }
    }

//
//
    @Override
    public GenericDeleteResponse<Integer> deletePreviousProject(PreviousProject previousProject) {
        GenericDeleteResponse<Integer> response=new GenericDeleteResponse<>();
        previousProjectRepository.delete(previousProject);
        Optional<PreviousProject> project= previousProjectRepository.findById(previousProject.getId());
        if (project.isPresent()){
            response.setSuccess(false);
            response.setMessage("Somthing went wrong please try again");
            return response;

        }
        else {
            response.setSuccess(true);
            return response;
        }

    }
}
