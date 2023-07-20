package com.Estructura.API.requests.projects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectRequest {
    private String name;
    private String description;
    private Double cost;
    private String location;
    private boolean projectFromEstructura;
    private Integer professionalId;
    private MultipartFile mainImage;
    private List<MultipartFile> extraImages;
    private List<MultipartFile> documents;
}
