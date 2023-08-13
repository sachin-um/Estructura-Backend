package com.Estructura.API.requests.customerRequests;

import com.Estructura.API.model.RetailItemType;
import com.Estructura.API.model.Role;
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
public class customerRequest {
    private String description;
    private String shortDesc;
    private List<Role> targetCategories;
    private List<RetailItemType> targetRetailCategories;
    private Double minPrice;
    private Double maxPrice;
    private List<MultipartFile> images;
    private List<MultipartFile> documents;
    private Integer customerId;
}
