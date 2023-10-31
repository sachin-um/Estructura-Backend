package com.Estructura.API.requests.serviceProviderResponses;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseRequest {
    private Long requestId;
    private Integer serviceProviderId;
    private String shortDesc;
    private String response;
    private Double proposedBudget;
    private List<MultipartFile> documents;
}
