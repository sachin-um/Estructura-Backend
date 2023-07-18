package com.Estructura.API.responses.projects;

import com.Estructura.API.model.PreviousProject;
import com.Estructura.API.model.Role;
import com.Estructura.API.model.User;
import com.Estructura.API.requests.projects.ProjectRequest;
import com.Estructura.API.responses.ValidatedResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponse{
    @JsonProperty("success")
    @Builder.Default
    private boolean success = false;
    @JsonProperty("error_message")
    private String errormessage;
    @JsonProperty("project")
    private PreviousProject project;

    @JsonProperty("projects")
    private List<PreviousProject> projects;
}
