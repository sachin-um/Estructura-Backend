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
        if (response.checkValidity(projectRequest)) {
            Optional<Professional> professional = professionalService.findById(projectRequest.getProfessionalId());
            if (professional.isPresent()) {

                PreviousProject previousProject = PreviousProject.builder()
                        .name(projectRequest.getName())
                        .description(projectRequest.getDescription())
                        .cost(projectRequest.getCost())
                        .projectFromEstructura(projectRequest.isProjectFromEstructura())
                        .professional(professional.get())
                        .build();
                saveImagesAndDocuments(projectRequest, previousProject);
                PreviousProject project = previousProjectRepository.save(previousProject);
                uploadImagesAndDocuments(projectRequest, project);
                response.setSuccess(true);
                response.setId(Long.valueOf(project.getId()));
            } else {
                response.addError("fatal", "Invalid professional ID");
            }
        }
        else {
            response.addError("fatal", "Bad Request");
        }
        return response;
    }

    @Override
    public ResponseEntity<PreviousProject> getPreviousProjectById(Integer id) {
        Optional<PreviousProject> previousProject= previousProjectRepository.findById(id);
        return previousProject.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public GenericAddOrUpdateResponse<ProjectRequest> updatePreviousProject(ProjectRequest projectRequest, Integer id) throws IOException {
        GenericAddOrUpdateResponse<ProjectRequest> response = new GenericAddOrUpdateResponse<>();
        if (response.checkValidity(projectRequest)){
            Optional<PreviousProject> existingProject=previousProjectRepository.findById(id);
            if (existingProject.isPresent()){
                PreviousProject project=existingProject.get();
                project.setName(projectRequest.getName());
                project.setDescription(projectRequest.getDescription());
                project.setCost(projectRequest.getCost());
                project.setProjectFromEstructura(projectRequest.isProjectFromEstructura());
                project.setLocation(projectRequest.getLocation());
                saveImagesAndDocuments(projectRequest, project);
                project = previousProjectRepository.save(project);
                uploadImagesAndDocuments(projectRequest, project);
                response.setSuccess(true);
                response.setId(Long.valueOf(project.getId()));
            }
            else {
                response.addError("fatal","Project doesn't exist");
            }
        }
        return response;
    }

    private void uploadImagesAndDocuments(ProjectRequest projectRequest, PreviousProject project) throws IOException {
        int count= 0;
        String uploadDir = "./files/project-files/" + project.getProfessional().getId() + "/" + project.getId();
        FileUploadUtil.saveImages(uploadDir, projectRequest.getMainImage(), project.getMainImageName(), projectRequest.getExtraImages(), project.getExtraImage1Name(), project.getExtraImage2Name(), project.getExtraImage3Name());
        if (projectRequest.getDocuments() != null) {
            for (MultipartFile document : projectRequest.getDocuments()) {
                if (!document.isEmpty()) {
                    if (count == 0) {
                        String fileName = StringUtils.cleanPath(project.getDocument1Name());
                        FileUploadUtil.saveFile(uploadDir, document, fileName);
                    }
                    if (count == 1) {
                        String fileName = StringUtils.cleanPath(project.getDocument2Name());
                        FileUploadUtil.saveFile(uploadDir, document, fileName);
                    }
                    if (count == 2) {
                        String fileName = StringUtils.cleanPath(project.getDocument3Name());
                        FileUploadUtil.saveFile(uploadDir, document, fileName);
                    }
                    count++;
                    String fileName = StringUtils.cleanPath(document.getOriginalFilename());
                    FileUploadUtil.saveFile(uploadDir, document, fileName);
                }
            }
        }
    }

    private void saveImagesAndDocuments(ProjectRequest projectRequest, PreviousProject project) {
        String mainImageName = null;
        if (projectRequest.getMainImage() != null) {
            mainImageName = StringUtils.cleanPath(projectRequest.getMainImage().getOriginalFilename());
        }
        if (mainImageName != null) {
            project.setMainImage(mainImageName);
            project.setMainImageName(FileUploadUtil.generateFileName(mainImageName));
        }
        if (projectRequest.getLocation() != null) {
            project.setLocation(projectRequest.getLocation());
        }
        int count = 0;
        if (projectRequest.getExtraImages() != null) {
            for (MultipartFile file : projectRequest.getExtraImages()) {
                if (!file.isEmpty()) {
                    String extraImageName = StringUtils.cleanPath(file.getOriginalFilename());
                    if (count == 0) project.setExtraImage1(extraImageName);
                    project.setExtraImage1Name(FileUploadUtil.generateFileName(extraImageName));//check the image count is less than 3
                    if (count == 1) project.setExtraImage2(extraImageName);
                    project.setExtraImage2Name(FileUploadUtil.generateFileName(extraImageName));
                    if (count == 3) project.setExtraImage3(extraImageName);
                    project.setExtraImage3Name(FileUploadUtil.generateFileName(extraImageName));
                    count++;
                }
            }
        }
        count = 0;
        if (projectRequest.getDocuments() != null) {
            for (MultipartFile document : projectRequest.getDocuments()) {
                if (!document.isEmpty()) {
                    String documentName = StringUtils.cleanPath(document.getOriginalFilename());
                    if (count == 0) project.setDocument1(documentName);
                    project.setDocument1Name(FileUploadUtil.generateFileName(documentName));//check the image count is less than 3
                    if (count == 1) project.setDocument2(documentName);
                    project.setDocument2Name(FileUploadUtil.generateFileName(documentName));
                    if (count == 3) project.setDocument3(documentName);
                    project.setDocument2Name(FileUploadUtil.generateFileName(documentName));
                    count++;
                }
            }
        }
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
