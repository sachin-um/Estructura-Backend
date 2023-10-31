package com.Estructura.API.requests.customerPlans;

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
public class CustomerPlanRequest {
    private String name;
    private Integer coverImageId;
    private Integer[] professionals;
    private Long[] retailItems;
    private Long[] rentingItems;
    private String note;
    private List<MultipartFile> images;
    private List<MultipartFile> documents;
    private Double budgets;
    private Integer userID;
}
